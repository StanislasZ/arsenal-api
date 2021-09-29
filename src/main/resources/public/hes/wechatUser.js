const app = new Vue({
    el: '#app',
    data() {
        return {
            totalCount: 0, //分页组件--数据总条数
            //表格的数据
            list: [

            ],
            listLoading: false,//数据加载等待动画
            listQuery: {
                pageNum: 1,//页码
                pageRow: 10,//每页条数
                name: '',
                wechatId: '',

            },

            //excel上传相关
            uploadDialog: {
                title: '导入excel',
                visible: false,   //默认对话框不可见
                uploadUrl: '/wechat/updateWechatUser?',
                fileList: [],
                listFromUpload: [],
                importBtnLoading: false, //导入按钮转圈，  默认不转圈
            }
        }
    },
    created() {
        this.getList()
    },
    methods: {
        //改变每页数量
        handleSizeChange(val) {
            this.listQuery.pageRow = val;
            this.listQuery.pageNum = 1;
        },
        //改变页码
        handleCurrentChange(val) {
            this.listQuery.pageNum = val;
        },
        getList() {
            this.listLoading = true;
            let query = this.listQuery;
            console.log("getList... query = " + JSON.stringify(query));

            this.listLoading = true;
            api.getWechatUserList(query).then(res => {
                // console.log("res = " + JSON.stringify(res, null, 2));
                res = res.data;
                if (res.code != 200) {
                    this.$message.error('获取列表失败！' + res.msg);
                    return;
                }
                this.listLoading = false;
                this.list = res.data.list;
                this.totalCount = res.data.totalCount;
                console.log("拉取列表 成功");

            }).catch(err => {
                this.$message.error('获取列表失败！')
                console.log(err)
            })


        },

        showUploadDialog() {
            this.uploadDialog.visible = true;
        },
        onFileUploadSuccess(response) {
            console.log("response = " + JSON.stringify(response));
            if (response.code != 200) {
                this.uploadDialog.fileList = [];
                this.$message.error(response.msg);
                return;
            }

            this.uploadDialog.listFromUpload = response.data;

            this.uploadDialog.fileList = [];
            this.uploadDialog.visible = false;
            this.getList();
            this.$message.success("上传成功，已全量替换");

        },
        //删除的回调
        handleFileRemove(file, fileList) {
            this.$message.success("已移除该文件");
            this.uploadDialog.listFromUpload = [];
        },

        dlTemplate() {
            window.open('/excel/dlTemplate/' + 'WECHATUSER')
        },

        exportExcel() {
            let url = '/wechat/exportWechatUserExcel?';
            // let query = {
            //     id: id
            // };
            // url = url + "query=" + encodeURI(JSON.stringify(query));
            console.log("url = " + url);
            window.open(url);
        },

    },
    watch: {
        "listQuery.name" (val, oldVal) {
            console.log("watch... listQuery.name, val = " + val);
            this.listQuery.pageNum = 1;
        },
        "listQuery.wechatId" (val, oldVal) {
            console.log("watch... listQuery.wechatId, val = " + val);
            this.listQuery.pageNum = 1;
        },

        listQuery: {
            handler() {
                this.getList()
            },
            deep: true
        }

    }
})
