package barber.user.mybarber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class MakePaymentActivity extends Fragment {
    public static final String KEY_STATUS = "mask";
    public static final String KEY_STATUS1 = "mask1";
    public static final String KEY_STATUS2 = "mask2";
    private MaterialButton proceedToPay;
    private RadioButton debitCard;
    private TextView statusDonated, totalAmount;
    private CardView DebitCardLayout;
    public boolean checkView = false;
    private TextInputEditText cardNumber, cvv, expiryDate;
    private TextInputLayout cardNumberError;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_make_payment, container, false);


        init(view);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        totalAmount=view.findViewById(R.id.amount);
        totalAmount.setText("RM "+sharedPreferences.getInt("totalPrice",0));
        DebitCardLayout.setVisibility(View.GONE);
        debitCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkView) {
                    DebitCardLayout.setVisibility(View.GONE);
                    checkView = false;
                } else {
                    DebitCardLayout.setVisibility(View.VISIBLE);
                    checkView = true;
                }

            }
        });

        cardNumber.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 16)
                    expiryDate.requestFocus();
                if (charSequence.toString().startsWith("4"))
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visa, 0);
                else if (charSequence.toString().startsWith("5"))
                    cardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mastercard, 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Object[] paddingSpans = editable.getSpans(0, editable.length(), DashSpan.class);
                for (Object span : paddingSpans) {
                    editable.removeSpan(span);
                }

                addSpans(editable);
            }

            private static final int GROUP_SIZE = 4;

            private void addSpans(Editable editable) {

                final int length = editable.length();
                for (int i = 1; i * (GROUP_SIZE) < length; i++) {
                    int index = i * GROUP_SIZE;
                    editable.setSpan(new DashSpan(), index - 1, index,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        });


        expiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int start, int before) {
                if (s.length() == 2) {
                    if (start == 2 && before == 1 && !s.toString().contains("/")) {
                        expiryDate.setText("" + s.toString().charAt(0));
                        expiryDate.setSelection(1);
                    } else {
                        expiryDate.setText(s + "/");
                        expiryDate.setSelection(3);
                    }
                }
                if (s.length() == 5)
                    cvv.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private boolean validCardNumber() {
        String cardNumberString = cardNumber.getText().toString();
        if (cardNumberString.isEmpty())
            cardNumberError.setError("Card NUmber Can't be Empty");
        else if (CheckAllDigit(cardNumberString) || cardNumberString.length() < 16)
            cardNumberError.setError("Invalid Card Number");
        else {
            cardNumberError.setError(null);
            return true;
        }
        return false;

    }

    private boolean CheckAllDigit(String cardNumberString) {
        for (int i = 0; i < cardNumberString.length(); i++) {
            if (cardNumberString.charAt(i) == '-')
                continue;
            if (cardNumberString.charAt(i) < '0' && cardNumberString.charAt(i) > '9')
                return true;
        }
        return false;
    }

    private boolean checkCVV(String cvv) {
        if (cvv.length() > 3)
            return false;
        return true;
    }

    private void init(View view) {
        proceedToPay = view.findViewById(R.id.proceed_to_pay);
        debitCard = view.findViewById(R.id.debit_card);
        statusDonated = view.findViewById(R.id.status);
        totalAmount = view.findViewById(R.id.amount);
        DebitCardLayout = view.findViewById(R.id.debit_card_layout);
        cardNumber = view.findViewById(R.id.card_number);
        cardNumberError = view.findViewById(R.id.error_card_number);
        expiryDate = view.findViewById(R.id.expiry_date);
        cvv = view.findViewById(R.id.cvv);
    }

    public class DashSpan extends ReplacementSpan {

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            float padding = paint.measureText("-", 0, 1);
            float textSize = paint.measureText(text, start, end);
            return (int) (padding + textSize);
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                         int bottom, @NonNull Paint paint) {
            canvas.drawText(text.subSequence(start, end) + "-", x, y, paint);
        }
    }
}