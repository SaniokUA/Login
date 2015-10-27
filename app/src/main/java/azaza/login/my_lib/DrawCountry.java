package azaza.login.my_lib;

import android.content.Context;
import android.graphics.drawable.Drawable;

import azaza.login.R;

/**
 * Created by Alex on 27.10.2015.
 */
public class DrawCountry {

    Context context;
    Drawable flag;


    public DrawCountry(Context context){
        this.context = context;
    }



    public Drawable getFlag(String country) {

        switch (country) {
            case "UA":
                flag = context.getResources().getDrawable(R.drawable.ua_flag);
                break;

            default:
                flag = context.getResources().getDrawable(R.drawable.ua_flag);
                break;
        }
        return flag;
    }
}
