package yellcast.com.yell.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xml.sax.helpers.DefaultHandler;

import java.net.MalformedURLException;
import java.net.URL;

import yellcast.com.yell.R;
import yellcast.com.yell.YellApplication;
import yellcast.com.yell.model.YellNode;
import yellcast.com.yell.model.YellNodeType;
import yellcast.com.net.AsyncXmlParseTask;
import yellcast.com.net.FormatValidator;
import yellcast.com.yell.validator.TravisSvgBuildBadgeValidator;

/**
 * A placeholder fragment containing a simple view.
 */
public class YellNodeForm extends Fragment {

    private static final String TAG = YellNodeForm.class.getCanonicalName();

    public YellNodeForm() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_yell_form, container, false);

        final EditText urlEdit = (EditText) rootView.findViewById(R.id.yell_url_edit);
        final EditText labelEdit = (EditText) rootView.findViewById(R.id.yell_label_edit);
        final RadioGroup typeGroup = (RadioGroup) rootView.findViewById(R.id.typeRadioGroup);
        typeGroup.check(R.id.typeRadioTravis);

        Button createButton = (Button) rootView.findViewById(R.id.yell_create_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final URL url = new URL(urlEdit.getText().toString());
                    if (((YellApplication)getActivity().getApplication()).getModel().contains(url.toExternalForm())) {
                        showValidationErrorMessage(R.string.url_exists);
                        return;
                    }
                    int checkedRadioButtonId = typeGroup.getCheckedRadioButtonId();

                    final YellNodeType type = getTypeByRadioButton(checkedRadioButtonId);
                    final FormatValidator validator = getFormatValidatorByRadioButton(checkedRadioButtonId);

                    new AsyncXmlParseTask(validator, new AsyncXmlParseTask.CompletionListener() {
                        @Override
                        public void onCompleted(DefaultHandler defaultHandler) {
                            Log.d(TAG, "validator reports: " + validator.isValid());
                            if (!validator.isValid()) {
                                showValidationErrorMessage(R.string.invalid_format);
                                return;
                            }
                            YellNode yellNode = YellNode.newInstance();
                            yellNode.setType(type);
                            yellNode.setLabel(labelEdit.getText().toString());
                            yellNode.setUrl(url.toExternalForm());

                            ((YellApplication)getActivity().getApplication()).getModel().add(yellNode);
                            getActivity().finish();
                        }
                    }).execute(url);

                } catch (MalformedURLException e) {
                    showValidationErrorMessage(R.string.invalid_url);
                }
            }
        });


        return rootView;
    }

    private FormatValidator getFormatValidatorByRadioButton(final int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.typeRadioJenkins:
            case R.id.typeRadioUp4sure:
            case R.id.typeRadioTravis:
                return new TravisSvgBuildBadgeValidator();
            default:
                return createRejectingFormValidator();
        }
    }

    private YellNodeType getTypeByRadioButton(final int checkedRadioButtonId) {
        switch(checkedRadioButtonId) {
            case R.id.typeRadioUp4sure:
                return YellNodeType.UP4SURE;
            case R.id.typeRadioTravis:
                return YellNodeType.TRAVIS;
            case R.id.typeRadioJenkins:
                return YellNodeType.JENKINS;
            default:
                return null;
        }
    }

    private void showValidationErrorMessage(int message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

    private FormatValidator createRejectingFormValidator() {
        return new FormatValidator() {
            @Override
            public boolean isValid() {
                return false;
            }
        };
    }
}
