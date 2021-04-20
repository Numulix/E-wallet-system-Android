package rs.raf.projekat1.jovan_babic_rn3018.recycler.diff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.jovan_babic_rn3018.models.Rashod;

public class RashodDiffItemCallback extends DiffUtil.ItemCallback<Rashod> {

    @Override
    public boolean areItemsTheSame(@NonNull Rashod oldItem, @NonNull Rashod newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Rashod oldItem, @NonNull Rashod newItem) {
        boolean finalFlag = oldItem.getNaslov().equals(newItem.getNaslov())
                && oldItem.getKolicina() == newItem.getKolicina();

        if (oldItem.getOpis() == null)
            finalFlag = finalFlag && (oldItem.getAudioFilePath()
                    .equals(newItem.getAudioFilePath()));
        else
            finalFlag = finalFlag && (oldItem.getOpis().equals(newItem.getOpis()));

        return finalFlag;
    }
}
