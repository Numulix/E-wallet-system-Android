package rs.raf.projekat1.jovan_babic_rn3018.models;

import java.util.UUID;

public class Rashod {

    private String id;
    private String naslov;
    private int kolicina;
    private String opis;
    private String audioFilePath;

    public Rashod(String naslov, int kolicina, String opis) {
        this.id = UUID.randomUUID().toString();
        this.naslov = naslov;
        this.kolicina = kolicina;
        this.opis = opis;
    }

    public Rashod(String naslov, String audioFilePath, int kolicina) {
        this.id = UUID.randomUUID().toString();
        this.naslov = naslov;
        this.kolicina = kolicina;
        this.audioFilePath = audioFilePath;
    }

    public String getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public int getKolicina() {
        return kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public void setAudioFilePath(String audioFilePath) {
        this.audioFilePath = audioFilePath;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
