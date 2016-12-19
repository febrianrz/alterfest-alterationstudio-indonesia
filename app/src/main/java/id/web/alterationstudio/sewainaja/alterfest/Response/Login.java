package id.web.alterationstudio.sewainaja.alterfest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by febrian on 12/19/16.
 */
public class Login {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("saldo")
    @Expose
    private int saldo;
    @SerializedName("user_key")
    @Expose
    private String userKey;
    @SerializedName("kategori")
    @Expose
    private List<LoginKategori> kategori = null;

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
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param gambar
     * The username
     */
    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    /**
     *
     * @return
     * The gambar
     */
    public String getGambar() {
        return gambar;
    }

    /**
     *
     * @param saldo
     * The username
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    /**
     *
     * @return
     * The username
     */
    public int getSaldo() {
        return saldo;
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
     * The userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     *
     * @param userKey
     * The user_key
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     *
     * @return
     * The kategori
     */
    public List<LoginKategori> getKategori() {
        return kategori;
    }

    /**
     *
     * @param kategori
     * The kategori
     */
    public void setKategori(List<LoginKategori> kategori) {
        this.kategori = kategori;
    }
}
