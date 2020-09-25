package com.Unicesumar.HAS;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.Unicesumar.HAS.data.HAS_Contract;


public class CursorAlarmeAdapter extends CursorAdapter {

    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public CursorAlarmeAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText = (TextView) view.findViewById(R.id.recycle_title);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_date_time);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info);
        mActiveImage = (ImageView) view.findViewById(R.id.active_image);
        mThumbnailImage = (ImageView) view.findViewById(R.id.thumbnail_image);

        int tituloIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_TITLE);
        int dataIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_DATE);
        int horaIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_TIME);
        int repetirIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_REPEAT);
        int naoRepetirIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_REPEAT_NO);
        int tipoRepeticaoIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_REPEAT_TYPE);
        int ativaIndiceColuna = cursor.getColumnIndex(HAS_Contract.HAS_Acesso.KEY_ACTIVE);

        String title = cursor.getString(tituloIndiceColuna);
        String date = cursor.getString(dataIndiceColuna);
        String time = cursor.getString(horaIndiceColuna);
        String repeat = cursor.getString(repetirIndiceColuna);
        String repeatNo = cursor.getString(naoRepetirIndiceColuna);
        String repeatType = cursor.getString(tipoRepeticaoIndiceColuna);
        String active = cursor.getString(ativaIndiceColuna);

        String dateTime = date + " " + time;


        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setActiveImage(active);




    }

    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Cria um ícone com cor aleatória e a primeira letra do título
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }

    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if(repeat.equals("true")){
            mRepeatInfoText.setText("A cada " + repeatNo + " " + repeatType + "(s)");
        }else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
    }

    // Muda a imagem para "On" ou "Off"
    public void setActiveImage(String active){
        if(active.equals("true")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }
    }
}
