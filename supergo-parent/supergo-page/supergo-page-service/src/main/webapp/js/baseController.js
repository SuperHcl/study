// 全局混入
Vue.mixin({
    //全局参数
    data:function(){
        return {};
    },
    //全局方法
    methods:{
        //根据参数名字获取参数
        getParameterByName: function (name) {
            return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
        },

        //文件上传方法
        //文件上传，返回的url需要赋值给指定的变量，这里是传入变量名字
        upload:function (urlVariableName) {
            //创建表单对象
            var formData = new FormData();
            //从当前文档中搜索文件上传表单，拼接第一个文件对象到表单中
            formData.append("file",document.querySelector('input[type=file]').files[0]);

            //发送请求执行文件上传
            axios({
                url:'/upload.shtml',
                headers:{
                    'Content-Type': 'multipart/form-data'
                },
                method:'post',
                data:formData
            }).then(function (response) {
                if(response.data.success){
                    //app.image_entity.url=response.data.message;
                    urlVariableName=urlVariableName+'="'+response.data.message+'"';
                    //将指定字符串解析成一个变量
                    eval(urlVariableName);
                }else{
                    alert(response.data.message);
                }
            })
        },

        //查询所有分类，并组装数据
        getItemCatList:function (variableName) {
            //查询所有分类数据
            axios.get('/itemCat/all.shtml').then(function (response) {
                //循环组装
                for(var i=0;i<response.data.length;i++){
                    //获取当前被循环的记录
                    var itemCat = response.data[i];
                    //获取id
                    var key = itemCat.id;
                    //获取分类名字
                    var value = itemCat.name;
                    //组装数据到itemCatList中
                    //app.itemCatList[key]=value;
                    app[variableName][key]=value;
                }
            });
        },


        //根据父节点ID查询菜单信息
        getItemCatListByParentId:function (parentId,grand) {
            axios.get('/itemCat/parent/'+parentId+'.shtml').then(function (response) {
                app['itemCatList'+grand]=response.data;
            });
        },

        //从选中的规格集合中找到对应的规格数据
        //atrrName:需要查找的key的名字，例如:attributeName
        //nameValue:需要查找的key的名字对应的值，例如：网络制式
        searchObject:function (atrrName,nameValue,list) {
            for(var i=0;i<list.length;i++){
                //获取当前被循环的对象
                var spec = list[i];

                //判断规格选项名字和传入的规格选项名字值是否相同
                if(spec[atrrName]==nameValue){
                    return spec;
                }
            }
        },
    },
    //全局初始调用
    created: function () {

    }
})