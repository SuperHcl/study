package com.example.springbootmybatis.util;

import com.example.springbootmybatis.entity.ColMapping;
import com.example.springbootmybatis.entity.NormalSqlStructure;
import com.example.springbootmybatis.entity.WhereMapping;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * sql解析工具类
 *
 * @author Hu.ChangLiang
 * @date 2023/5/30 12:02
 */
public class SqlParserUtil {
    private SqlParserUtil() {
    }

    /**
     * 解析出select语句中的字段名
     *
     * @param sql 需要解析的select语句
     * @return sql中的字段
     * @throws JSQLParserException 解析SQL异常
     */
    public static List<String> getSelectColumns(String sql) throws JSQLParserException {
        List<String> columns = new ArrayList<>();
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectItems = plain.getSelectItems();
        if (selectItems != null) {
            for (SelectItem selectitem : selectItems) {
                columns.add(selectitem.toString());
            }
        }
        return columns;
    }

    /**
     * 解析出select语句中的表名
     *
     * @param sql 需要解析的select语句
     * @return sql中的表名
     * @throws JSQLParserException 解析SQL异常
     */
    public static List<String> getSelectTables(String sql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(selectStatement);
    }

    /**
     * 获取update语句操作的表名
     *
     * @param sql update语句
     * @return update的表
     * @throws JSQLParserException 解析SQL异常
     */
    public static List<String> getUpdateTable(String sql) throws JSQLParserException {
        Update updateStatement = (Update) CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(updateStatement);
    }

    public static List<String> getInsertTable(String sql) throws JSQLParserException {
        Insert insertStatement = (Insert) CCJSqlParserUtil.parse(sql);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(insertStatement);
        return tableList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取update语句set的字段
     *
     * @param sql update语句
     * @return update语句set的字段
     * @throws JSQLParserException 解析SQL异常
     */
    public static List<String> getUpdateColumns(String sql) throws JSQLParserException {
        List<String> columns = new ArrayList<>();
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Update update = (Update) parserManager.parse(new StringReader(sql));

        List<Column> updateSetColumns = update.getColumns();
        if (!CollectionUtils.isEmpty(updateSetColumns)) {
            for (Column updateSetColumn : updateSetColumns) {
                columns.add(updateSetColumn.toString());
            }
        }

        return columns;
    }

    /**
     * 解析表的别名和表名的映射
     *
     * @param sql 需要解析的sql
     * @return <key>别名</key>, <value>表名</value>
     * @throws JSQLParserException 解析SQL异常
     */
    public static Map<String, String> getTableAlias(String sql) throws JSQLParserException {
        Map<String, String> result = new HashMap<>();
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        Table table = (Table) plainSelect.getFromItem();
        if (Objects.nonNull(table.getAlias())) {
            result.put(table.getAlias().getName(), table.getName());
        }
        if(Objects.nonNull(plainSelect.getJoins())){
            for (Join join : plainSelect.getJoins()) {
                Table rightItem = (Table) join.getRightItem();
                if (Objects.nonNull(rightItem.getAlias())) {
                    result.put(rightItem.getAlias().getName(), rightItem.getName());
                }
            }
        }
        return result;
    }

    /**
     * 解析select sql语句结构
     *
     * @param sql     select sql
     * @param isAlias true|false 是否使用别称<br> eg. 【s_id as id】 => 【id】<br>
     * @return 标准化SQL结构
     * @throws JSQLParserException 解析SQL异常
     */
    public static NormalSqlStructure getSelectStructure(String sql, boolean isAlias) throws JSQLParserException {
        NormalSqlStructure normalSqlStructureDto = new NormalSqlStructure();

        normalSqlStructureDto.setSql(sql);
        sql = sql.replaceAll("(\\$\\{\\w*})|(\\{\\{\\w+}})", "''");
        // 1.解析表名
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        // 解析SQL为Statement对象
        Statement statement = parserManager.parse(new StringReader(sql));

        // 创建表名发现者对象
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        // 获取到表名列表
        List<String> tableNameList = tablesNamesFinder.getTableList(statement);
        normalSqlStructureDto.setTableNames(tableNameList);
        // 表别名映射
        Map<String, String> tableMapping = getTableAlias(sql);
        // 字段和表的映射
        List<ColMapping> colMappingList = new ArrayList<>();

        // 2.解析查询元素 列，函数等
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        // 3.解析where条件的字段
        List<WhereMapping> whereMappings = getWhereMappings(plainSelect);
        normalSqlStructureDto.setWhereMappings(whereMappings);

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        List<String> columnList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(selectItems)) {
            for (SelectItem selectItem : selectItems) {
                ColMapping colMapping = new ColMapping();
                String columnName;
                String tblAlias = "";
                try {
                    if (selectItem instanceof SelectExpressionItem) {
                        SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                        Alias alias = selectExpressionItem.getAlias();
                        Expression expression = selectExpressionItem.getExpression();
                        Column col = ((Column) expression);
                        Table colTbl = col.getTable();
                        if (Objects.nonNull(colTbl)) {
                            tblAlias = colTbl.getName();
                        }
                        if (!isAlias) {
                            columnName = selectItem.toString();
                        } else {
                            if (alias != null) {
                                columnName = alias.getName();
                            } else {
                                SimpleNode node = expression.getASTNode();
                                Object value = node.jjtGetValue();
                                if (value instanceof Column) {
                                    columnName = ((Column) value).getColumnName();
                                } else if (value instanceof Function) {
                                    columnName = value.toString();
                                } else {
                                    // 增加对select 'aaa' from table; 的支持
                                    columnName = String.valueOf(value);
                                    columnName = columnName.replace("'", "");
                                    columnName = columnName.replace("\"", "");
                                    columnName = columnName.replace("`", "");
                                }
                            }
                        }

                        columnName = columnName.replace("'", "");
                        columnName = columnName.replace("\"", "");
                        columnName = columnName.replace("`", "");

                        colMapping.setName(col.getColumnName());
                        if (Objects.nonNull(alias) && StringUtils.hasText(alias.getName())) {
                            colMapping.setAlias(alias.getName());
                        }
                        colMapping.setTable(tableMapping.get(tblAlias));

                    } else if (selectItem instanceof AllTableColumns) {
                        AllTableColumns allTableColumns = (AllTableColumns) selectItem;
                        columnName = allTableColumns.toString();
                        if (columnName.contains(".")) {
                            tblAlias = columnName.substring(0, columnName.indexOf(".")).trim();
                            colMapping.setTable(tableMapping.get(tblAlias));
                        } else {
                            colMapping.setTable(tableNameList);
                        }
                        colMapping.setName(columnName);
                    } else if (selectItem.toString().equals("*")) {
                        columnName = selectItem.toString();
                        colMapping.setName(columnName);
                        colMapping.setTable(tableNameList);
                    } else {
                        columnName = selectItem.toString();
                        colMapping.setName(columnName);
                        colMapping.setType("varchar");
                    }
                } catch (Exception e) {
                    columnName = selectItem.toString();
                    colMapping.setName(columnName);
                    colMapping.setType("varchar");
                    colMapping.setTable(null);
                }

                columnList.add(columnName);
                colMappingList.add(colMapping);
            }
            normalSqlStructureDto.setSelectItems(columnList);
            normalSqlStructureDto.setColMappings(colMappingList);
        }

        return normalSqlStructureDto;
    }

    /**
     * 解析update SQL语句结构
     *
     * @param sql update sql
     * @return 标准化SQL结构
     * @throws JSQLParserException 解析SQL异常
     */
    public static NormalSqlStructure getUpdateStructure(String sql) throws JSQLParserException {
        NormalSqlStructure normalSqlStructure = new NormalSqlStructure();
        normalSqlStructure.setSql(sql);
        List<String> updateTable = getUpdateTable(sql);
        List<String> updateColumns = getUpdateColumns(sql);
        normalSqlStructure.setTableNames(updateTable);
        normalSqlStructure.setSelectItems(updateColumns);
        return normalSqlStructure;
    }

    /**
     * 解析insert SQL语句结构
     *
     * @param sql insert sql
     * @return 标准化SQL结构
     * @throws JSQLParserException 解析SQL异常
     */
    public static NormalSqlStructure getInsertStructure(String sql) throws JSQLParserException {
        NormalSqlStructure normalSqlStructure = new NormalSqlStructure();
        normalSqlStructure.setSql(sql);
        normalSqlStructure.setTableNames(getInsertTable(sql));
        return normalSqlStructure;
    }

    // 找到包含指定 Column 的 SelectExpressionItem
    private static SelectExpressionItem findSelectItemByColumn(PlainSelect plainSelect, Column column) {
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        for (SelectItem selectItem : selectItems) {
            if (selectItem instanceof SelectExpressionItem) {
                Expression expression = ((SelectExpressionItem) selectItem).getExpression();
                if (expression instanceof Column && ((Column) expression).equals(column)) {
                    return (SelectExpressionItem) selectItem;
                }
            }
        }
        return null;
    }

    public static List<WhereMapping> getWhereMappings(PlainSelect plainSelect) {
        List<WhereMapping> whereMappings = new ArrayList<>();
        if (Objects.nonNull(plainSelect.getWhere())) {
            plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                @Override
                public void visit(Column column) {
                    WhereMapping whereMapping = new WhereMapping();
                    String columnName = column.getColumnName();
                    whereMapping.setName(columnName);
                    // 获取字段的别名
                    String fullyQualifiedName = column.getFullyQualifiedName();
                    if(StringUtils.hasText(fullyQualifiedName) && fullyQualifiedName.contains(".")) {
                        String tableAlias = fullyQualifiedName.substring(0, fullyQualifiedName.indexOf("."));
                        whereMapping.setTableAlias(tableAlias);
                    }
                    whereMappings.add(whereMapping);
                }
            });
        }
        return whereMappings;
    }
}
