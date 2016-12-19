package id.web.alterationstudio.sewainaja.alterfest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by febrian on 12/19/16.
 */
public class GetTransaksi {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<GetTransaksiData> data = null;

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The data
     */
    public List<GetTransaksiData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<GetTransaksiData> data) {
        this.data = data;
    }
}
