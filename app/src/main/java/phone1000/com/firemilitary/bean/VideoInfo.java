package phone1000.com.firemilitary.bean;

import java.util.List;

/**
 * Created by Jerry 2016/9/5 13:57
 */
public class VideoInfo {

    /**
     * status : 0
     * message : ok
     * data : {"list":[{"tid":"40136","videoid":"98","replys":"1","title":"乌克兰要卖巨无霸宝贝 中国买家能造出第二架安225吗？","dateline":"1472952148","image_list":"http://img.fenghuo001.com/video_img/318/401361472952141.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"40021","videoid":"97","replys":"0","title":"破解白银案背后基因技术 美军曾用同样办法找到本拉登","dateline":"1472790908","image_list":"http://img.fenghuo001.com/video_img/318/400211472790901.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39951","videoid":"96","replys":"1","title":"米格战机辉煌不再 没了苏联没人买","dateline":"1472707337","image_list":"http://img.fenghuo001.com/video_img/318/399511472707322.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39865","videoid":"95","replys":"0","title":"韩国想以核制核应对朝鲜 原子弹项目曾被美日搅乱","dateline":"1472610079","image_list":"http://img.fenghuo001.com/video_img/318/398651472610073.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39789","videoid":"94","replys":"0","title":"L-15猎鹰入役空军亮相开放日 未来还将发展舰载型？","dateline":"1472534128","image_list":"http://img.fenghuo001.com/video_img/318/397891472534111.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39578","videoid":"93","replys":"3","title":"纳粹黑科技：会飞的打飞机大炮","dateline":"1472259374","image_list":"http://img.fenghuo001.com/video_img/318/395781472259258.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39437","videoid":"92","replys":"1","title":"日本又要魔改F-15J 自卫队又遭遇怎样的难题","dateline":"1472104528","image_list":"http://img.fenghuo001.com/video_img/318/394371472104498.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39272","videoid":"91","replys":"1","title":"美俄争着卖 印度何需发展自己的国防工业","dateline":"1471943061","image_list":"http://img.fenghuo001.com/video_img/318/392721471943047.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39136","videoid":"90","replys":"0","title":"美海军下一代舰载机存短板 未来将如何反舰？","dateline":"1471834772","image_list":"http://img.fenghuo001.com/video_img/318/391361471834764.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"38786","videoid":"89","replys":"6","title":"美军也忙着搞航母杀手 现已部署关岛","dateline":"1471486081","image_list":"http://img.fenghuo001.com/video_img//387861471486190.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"}],"total_page":7}
     */

    private int status;
    private String message;
    /**
     * list : [{"tid":"40136","videoid":"98","replys":"1","title":"乌克兰要卖巨无霸宝贝 中国买家能造出第二架安225吗？","dateline":"1472952148","image_list":"http://img.fenghuo001.com/video_img/318/401361472952141.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"40021","videoid":"97","replys":"0","title":"破解白银案背后基因技术 美军曾用同样办法找到本拉登","dateline":"1472790908","image_list":"http://img.fenghuo001.com/video_img/318/400211472790901.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39951","videoid":"96","replys":"1","title":"米格战机辉煌不再 没了苏联没人买","dateline":"1472707337","image_list":"http://img.fenghuo001.com/video_img/318/399511472707322.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39865","videoid":"95","replys":"0","title":"韩国想以核制核应对朝鲜 原子弹项目曾被美日搅乱","dateline":"1472610079","image_list":"http://img.fenghuo001.com/video_img/318/398651472610073.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39789","videoid":"94","replys":"0","title":"L-15猎鹰入役空军亮相开放日 未来还将发展舰载型？","dateline":"1472534128","image_list":"http://img.fenghuo001.com/video_img/318/397891472534111.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39578","videoid":"93","replys":"3","title":"纳粹黑科技：会飞的打飞机大炮","dateline":"1472259374","image_list":"http://img.fenghuo001.com/video_img/318/395781472259258.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39437","videoid":"92","replys":"1","title":"日本又要魔改F-15J 自卫队又遭遇怎样的难题","dateline":"1472104528","image_list":"http://img.fenghuo001.com/video_img/318/394371472104498.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39272","videoid":"91","replys":"1","title":"美俄争着卖 印度何需发展自己的国防工业","dateline":"1471943061","image_list":"http://img.fenghuo001.com/video_img/318/392721471943047.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"39136","videoid":"90","replys":"0","title":"美海军下一代舰载机存短板 未来将如何反舰？","dateline":"1471834772","image_list":"http://img.fenghuo001.com/video_img/318/391361471834764.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"},{"tid":"38786","videoid":"89","replys":"6","title":"美军也忙着搞航母杀手 现已部署关岛","dateline":"1471486081","image_list":"http://img.fenghuo001.com/video_img//387861471486190.jpg","show_type":4,"nickname":"氕氘氚","content_type":1,"list_from":"烽火军事"}]
     * total_page : 7
     */

    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int total_page;
        /**
         * tid : 40136
         * videoid : 98
         * replys : 1
         * title : 乌克兰要卖巨无霸宝贝 中国买家能造出第二架安225吗？
         * dateline : 1472952148
         * image_list : http://img.fenghuo001.com/video_img/318/401361472952141.jpg
         * show_type : 4
         * nickname : 氕氘氚
         * content_type : 1
         * list_from : 烽火军事
         */

        private List<ListBean> list;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String tid;
            private String videoid;
            private String replys;
            private String title;
            private String dateline;
            private String image_list;
            private int show_type;
            private String nickname;
            private int content_type;
            private String list_from;

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getVideoid() {
                return videoid;
            }

            public void setVideoid(String videoid) {
                this.videoid = videoid;
            }

            public String getReplys() {
                return replys;
            }

            public void setReplys(String replys) {
                this.replys = replys;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDateline() {
                return dateline;
            }

            public void setDateline(String dateline) {
                this.dateline = dateline;
            }

            public String getImage_list() {
                return image_list;
            }

            public void setImage_list(String image_list) {
                this.image_list = image_list;
            }

            public int getShow_type() {
                return show_type;
            }

            public void setShow_type(int show_type) {
                this.show_type = show_type;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getContent_type() {
                return content_type;
            }

            public void setContent_type(int content_type) {
                this.content_type = content_type;
            }

            public String getList_from() {
                return list_from;
            }

            public void setList_from(String list_from) {
                this.list_from = list_from;
            }
        }
    }
}
