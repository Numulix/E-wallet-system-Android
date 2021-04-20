package rs.raf.projekat1.jovan_babic_rn3018.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat1.jovan_babic_rn3018.models.Prihod;
import rs.raf.projekat1.jovan_babic_rn3018.models.Rashod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Prihod>> prihodi = new MutableLiveData<>();
    private final ArrayList<Prihod> prihodiList = new ArrayList<>();

    private final MutableLiveData<List<Rashod>> rashodi = new MutableLiveData<>();
    private final ArrayList<Rashod> rashodList = new ArrayList<>();

    public MainViewModel() {
        Random random = new Random();
        int numPrihod = random.nextInt(25);
        int numRashod = random.nextInt(25);

        for (int i = 0; i <= numPrihod; i++) {
            Prihod prihod = new Prihod("Prihod " + i,
                    random.nextInt(11)*1000,
                    "Ovo je opis nekog prihoda");
            prihodiList.add(prihod);
        }

        ArrayList<Prihod> listToSubmitPrihod = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmitPrihod);

        for (int i = 0; i <= numRashod; i++) {
            Rashod rashod = new Rashod("Rashod " + i,
                    random.nextInt(11)*1000,
                    "Ovo je opis nekog rashoda");
            rashodList.add(rashod);
        }

        ArrayList<Rashod> listToSubmitRashod = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmitRashod);
    }

    public int getPrihodiSum() {
        int sum = 0;
        for (Prihod p: prihodiList) sum += p.getKolicina();
        return sum;
    }

    public int getRashodiSum() {
        int sum = 0;
        for (Rashod r: rashodList) sum += r.getKolicina();
        return sum;
    }

    public void addPrihod(String name, int kolicina, String description) {
        Prihod prihod = new Prihod(name, kolicina, description);
        prihodiList.add(prihod);
        ArrayList<Prihod> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);
    }

    public void addRashod(String name, int kolicina, String description) {
        Rashod rashod = new Rashod(name, kolicina, description);
        rashodList.add(rashod);
        ArrayList<Rashod> listToSubmit = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmit);
    }

    public void addPrihodWithAudio(String name, int kolicina, String audioFile){
        Prihod prihod = new Prihod(name, audioFile, kolicina);
        prihodiList.add(prihod);
        ArrayList<Prihod> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);
    }

    public void addRashodWithAudio(String name, int kolicina, String audioFile){
        Rashod rashod = new Rashod(name, audioFile, kolicina);
        rashodList.add(rashod);
        ArrayList<Rashod> listToSubmit = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmit);
    }

    public LiveData<List<Prihod>> getPrihodi() {
        return prihodi;
    }

    public LiveData<List<Rashod>> getRashodi() {
        return rashodi;
    }

    public void removePrihod(Prihod prihod) {
        prihodiList.remove(prihod);
        ArrayList<Prihod> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);
    }

    public void removeRashod(Rashod rashod) {
        rashodList.remove(rashod);
        ArrayList<Rashod> listToSubmit = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmit);
    }

    public void changePrihod(String id, String naziv, int kolicina, String opis) {
        for (Prihod p: prihodiList) {
            if (p.getId().equals(id)) {
                p.setNaslov(naziv);
                p.setKolicina(kolicina);
                p.setOpis(opis);
                break;
            }
        }
        ArrayList<Prihod> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);
    }

    public void changeRashod(String id, String naziv, int kolicina, String opis) {
        for (Rashod r: rashodList) {
            if (r.getId().equals(id)) {
                r.setNaslov(naziv);
                r.setKolicina(kolicina);
                r.setOpis(opis);
                break;
            }
        }
        ArrayList<Rashod> listToSubmit = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmit);
    }


    public void changePrihodWithAudio(String id, String naziv, int kolicina, String audioFilePath) {
        for (Prihod p: prihodiList) {
            if (p.getId().equals(id)) {
                p.setNaslov(naziv);
                p.setKolicina(kolicina);
                p.setAudioFilePath(audioFilePath);
                break;
            }
        }
        ArrayList<Prihod> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);
    }

    public void changeRashodWithAudio(String id, String naziv, int kolicina, String audioFilePath) {
        for (Rashod r: rashodList) {
            if (r.getId().equals(id)) {
                r.setNaslov(naziv);
                r.setKolicina(kolicina);
                r.setAudioFilePath(audioFilePath);
                break;
            }
        }
        ArrayList<Rashod> listToSubmit = new ArrayList<>(rashodList);
        rashodi.setValue(listToSubmit);
    }
}
