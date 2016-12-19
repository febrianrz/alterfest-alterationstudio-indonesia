package id.web.alterationstudio.sewainaja.alterfest.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by febrian on 12/19/16.
 */
public class GetTransaksiData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("nominal")
    @Expose
    private Integer nominal;

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
     * The judul
     */
    public String getJudul() {
        return judul;
    }

    /**
     *
     * @param judul
     * The judul
     */
    public void setJudul(String judul) {
        this.judul = judul;
    }

    /**
     *
     * @return
     * The keterangan
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     *
     * @param keterangan
     * The keterangan
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    /**
     *
     * @return
     * The jenis
     */
    public String getJenis() {
        return jenis;
    }

    /**
     *
     * @param jenis
     * The jenis
     */
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    /**
     *
     * @return
     * The kategori
     */
    public String getKategori() {
        return kategori;
    }

    /**
     *
     * @param kategori
     * The kategori
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     *
     * @return
     * The tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     *
     * @param tanggal
     * The tanggal
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     *
     * @return
     * The nominal
     */
    public Integer getNominal() {
        return nominal;
    }

    /**
     *
     * @param nominal
     * The nominal
     */
    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }
}
