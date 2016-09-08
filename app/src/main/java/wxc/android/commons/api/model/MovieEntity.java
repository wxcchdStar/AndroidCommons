package wxc.android.commons.api.model;

import java.util.List;

public class MovieEntity {

    /**
     * max : 10
     * average : 9.6
     * stars : 50
     * min : 0
     */

    public RatingBean rating;
    /**
     * rating : {"max":10,"average":9.6,"stars":"50","min":0}
     * genres : ["犯罪","剧情"]
     * title : 肖申克的救赎
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
     * collect_count : 949840
     * original_title : The Shawshank Redemption
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
     * year : 1994
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
     * alt : https://movie.douban.com/subject/1292052/
     * id : 1292052
     */

    public String title;
    public int collect_count;
    public String original_title;
    public String subtype;
    public String year;
    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
     */

    public ImagesBean images;
    public String alt;
    public String id;
    public List<String> genres;
    /**
     * alt : https://movie.douban.com/celebrity/1054521/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
     * name : 蒂姆·罗宾斯
     * id : 1054521
     */

    public List<CastsBean> casts;
    /**
     * alt : https://movie.douban.com/celebrity/1047973/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
     * name : 弗兰克·德拉邦特
     * id : 1047973
     */

    public List<DirectorsBean> directors;

    public static class RatingBean {
        public int max;
        public double average;
        public String stars;
        public int min;

    }

    public static class ImagesBean {
        public String small;
        public String large;
        public String medium;
    }

    public static class CastsBean {
        public String alt;
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
         */

        public AvatarsBean avatars;
        public String name;
        public String id;

        public static class AvatarsBean {
            public String small;
            public String large;
            public String medium;
        }
    }

    public static class DirectorsBean {
        public String alt;
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
         */

        public AvatarsBean avatars;
        public String name;
        public String id;

        public static class AvatarsBean {
            public String small;
            public String large;
            public String medium;
        }
    }
}
