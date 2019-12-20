/*****
 * 定义一个Content控制层 controller
 * 发送HTTP请求和后台进行数据交互
 ****/
var app=new Vue({
    el:'#app',
    data:{
        specificationItems:{},  //记录用户选中的规格信息
        num:1,          //记录用户购买的数量信息
        sku:{},      //用户选中的sku对象
    },
    methods:{
        //加入购物车实现
        addCart:function () {
            alert('恭喜您，已经将商品加入到购物车！ID：'+app.sku.id+',价格是：'+app.sku.price+',购买了'+app.num);

            //发送远程请求，加入购物车操作
            let url = 'http://localhost:18093/cart/add.shtml?itemId='+app.sku.id+'&num='+app.num;

            //加入购物车  {'withCredentials':true}:发送客户端的Cookie数据
            axios.get(url,{'withCredentials':true}).then(function (response) {
                if(response.data.success){
                    location.href='http://localhost:18093/cart.html';
                }else{
                    alert(response.data.message);
                }
            })
            
        },

        //判断2个Map结构是否匹配
        matchObject:function (map1,map2) {
            //循环一个map
            for(var key in map1){
                //获取当前循环的map的key,再获取当前key的值
                let m1v = map1[key];
                //获取另一个map相同key的值
                let m2v = map2[key];
                //不同，则返回false，表明不相同
                if(m1v!=m2v){
                    return false;
                }
            }
            return true;
        },


        //每次点击，判断当前选中的规格属于哪个SKU的
        searchSku:function () {
            //循环所有的SKU
            for(var i=0;i<itemList.length;i++){
                //判断当前被循环的SKU的spec值和选中的规格值是否相同
                if(this.matchObject(this.specificationItems,JSON.parse(itemList[i].spec))){
                    //如果相同，则把当前被循环的SKU赋值给app.sku  深克隆
                    app.sku=JSON.parse(JSON.stringify(itemList[i]));
                    return;
                }
            }

            //该商品下架了
            this.sku={'title':'该商品断货了','price':0,'id':0,spec:{}};
        },


        //定义方法获取SKU数据
        loadSku:function () {
            //从集合中获取第1个记录
            //深克隆
            this.sku =JSON.parse(JSON.stringify(itemList[0]));
            //将spce赋值给选中的specificationItems
            this.specificationItems=JSON.parse(this.sku.spec);
        },


        //创建方法，实现用户购买数量增加操作
        addNum:function (x) {
            this.num+=x;
            if(this.num<1){
                this.num=1;
            }
        },

        //创建一个方法实现记录操作
        //key:规格   网络
        //value:规格选项   移动4G
        selectSpecification:function (key,value) {
            app.$set(app.specificationItems,key,value);

            //查找当前SKU
            this.searchSku();
        }
    },
    created:function () {
        //加载默认的SKU
        this.loadSku();
    }
});
