package id.web.alterationstudio.sewainaja.alterfest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by febrian on 12/19/16.
 */
public class LoginKategori {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The nama
     */
    public String getNama() {
        return nama;
    }

    /**
     *
     * @param nama
     * The nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

}
