package android.particles.com.retrofit.modules.main.domain;

/**
 * Created by YLM on 2016/5/1.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Word {

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("trans_result")
    @Expose
    private List<TransResult> transResult = new ArrayList<TransResult>();

    /**
     *
     * @return
     * The from
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     * The to
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to
     * The to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @return
     * The transResult
     */
    public List<TransResult> getTransResult() {
        return transResult;
    }

    /**
     *
     * @param transResult
     * The trans_result
     */
    public void setTransResult(List<TransResult> transResult) {
        this.transResult = transResult;
    }

}