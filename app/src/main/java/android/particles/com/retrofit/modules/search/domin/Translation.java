package android.particles.com.retrofit.modules.search.domin;

import java.util.List;

/**
 * Created by YLM on 2016/11/20.
 */
public class Translation {

    /**
     * translation : ["苹果"]
     * basic : {"us-phonetic":"'æpl","phonetic":"'æp(ə)l","uk-phonetic":"'æp(ə)l","explains":["n. 苹果，苹果树，苹果似的东西；[美俚]炸弹，手榴弹，（棒球的）球；[美俚]人，家伙。"]}
     * query : apple
     * errorCode : 0
     * web : [{"value":["苹果","苹果","苹果公司"],"key":"APPLE"},{"value":["Apple Lossless","Apple Lossless","애플 무손실"],"key":"Apple Lossless"},{"value":["苹果商店","苹果零售店","苹果商店"],"key":"Apple Store"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        /**
         * us-phonetic : 'æpl
         * phonetic : 'æp(ə)l
         * uk-phonetic : 'æp(ə)l
         * explains : ["n. 苹果，苹果树，苹果似的东西；[美俚]炸弹，手榴弹，（棒球的）球；[美俚]人，家伙。"]
         */

        private String phonetic;
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * value : ["苹果","苹果","苹果公司"]
         * key : APPLE
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
