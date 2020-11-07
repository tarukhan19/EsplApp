package proj.me.bitframe;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by root on 13/3/16.
 */
 public class BinadingBitFrame extends BaseObservable{
    int progressBarColor;
    boolean progressBarVisibility;

    @Bindable public boolean isProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(boolean progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
		notifyPropertyChanged(BR.progressBarVisibility);
    }
    @Bindable
    public int getProgressBarColor() {
        return progressBarColor;
    }

    public void setProgressBarColor(int progressBarColor) {
        this.progressBarColor = progressBarColor;
		notifyPropertyChanged(BR.progressBarColor);
    }

}
