package android.particles.com.retrofit.modules.main.domain;

/**
 * Created by YLM on 2016/5/5.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TransResult {

    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("dst")
    @Expose
    private String dst;

    /**
     *
     * @return
     * The src
     */
    public String getSrc() {
        return src;
    }

    /**
     *
     * @param src
     * The src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     *
     * @return
     * The dst
     */
    public String getDst() {
        return dst;
    }

    /**
     *
     * @param dst
     * The dst
     */
    public void setDst(String dst) {
        this.dst = dst;
    }

}
