package com.br.fragment;

import java.util.ArrayList;
import java.util.List;

import com.br.activity.R;
import com.br.dao.LinguagemDataSource;

import com.br.model.MyObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class TituloAreaEstratoFragment extends Fragment implements
		OnItemClickListener, OnCheckedChangeListener {
	protected static final String TAG = null;
	private AutoCompleteTextView text;
	private static String[] area = { "ENGENHARIAS IV",
			"ADMINISTRAÇÃO, CIÊNCIAS CONTÁBEIS E TURISMO",
			"ANTROPOLOGIA / ARQUEOLOGIA", "ARQUITETURA E URBANISMO",
			"ARTES / MÚSICA", "ASTRONOMIA / FÍSICA", "BIODIVERSIDADE",
			"BIOTECNOLOGIA", "CIÊNCIA DA COMPUTAÇÃO", "CIÊNCIA DE ALIMENTOS",
			"CIÊNCIA POLÍTICA E RELAÇÕES INTERNACIONAIS",
			"CIÊNCIAS AGRÁRIAS I", "CIÊNCIAS AMBIENTAIS",
			"CIÊNCIAS BIOLÓGICAS I", "CIÊNCIAS BIOLÓGICAS II",
			"CIÊNCIAS BIOLÓGICAS III", "CIÊNCIAS SOCIAIS APLICADAS I",
			"DIREITO", "ECONOMIA", "EDUCAÇÃO", "EDUCAÇÃO FÍSICA", "ENFERMAGEM",
			"ENGENHARIAS I", "ENGENHARIAS II", "ENGENHARIAS III", "ENSINO",
			"FARMÁCIA", "FILOSOFIA/TEOLOGIA:subcomissão FILOSOFIA",
			"FILOSOFIA/TEOLOGIA:subcomissão TEOLOGIA", "GEOCIÊNCIAS",
			"GEOGRAFIA", "HISTÓRIA", "INTERDISCIPLINAR",
			"LETRAS / LINGUÍSTICA", "MATEMÁTICA / PROBABILIDADE E",
			"MATERIAIS", "MEDICINA", "MEDICINA I", "MEDICINA II",
			"MEDICINA III", "MEDICINA VETERINÁRIA", "NUTRIÇÃO", "ODONTOLOGIA",
			"PLANEJAMENTO URBANO E REGIONAL / DEMOGRAFIA", "PSICOLOGIA",
			"QUÍMICA", "SAÚDE COLETIVA", "SERVIÇO SOCIAL", "SOCIOLOGIA",
			"ZOOTECNIA / RECURSOS PESQUEIROS" };
	// private String[] estrato = { "A1", "A2", "B1", "B2", "B3", "B4", "B5",
	// "C" };
	private Spinner sp = null;
	// private RadioButton radionBtn;

	protected FragmentActivity activity;
	CheckBox a1, a2, b1, b2, b3, b4, b5, c, todos;
	LinguagemDataSource lst;
	String[] item = new String[] { "Please search..." };
	ArrayAdapter<String> adp1;

	// private RadioGroup mRadioGroup;
	// RadioGroup botaoRG;
	// RadioButton botaoRB;
	// Button ok;

	// private Spinner sp2 = null;

	public TituloAreaEstratoFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sp = (Spinner) getActivity().findViewById(R.id.spinner3);

		ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, area);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp.setAdapter(adp);

		a1 = (CheckBox) getActivity().findViewById(R.id.checkBox_A1);
		a2 = (CheckBox) getActivity().findViewById(R.id.checkBox_A2);
		b1 = (CheckBox) getActivity().findViewById(R.id.checkBox_B1);
		b2 = (CheckBox) getActivity().findViewById(R.id.checkBox_B2);
		b3 = (CheckBox) getActivity().findViewById(R.id.checkBox_B3);
		b4 = (CheckBox) getActivity().findViewById(R.id.checkBox_B4);
		b5 = (CheckBox) getActivity().findViewById(R.id.checkBox_B5);
		c = (CheckBox) getActivity().findViewById(R.id.checkBox_C);
		todos = (CheckBox) getActivity().findViewById(R.id.checkBox_todos);
		todos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if (isChecked) {
					a1.setEnabled(false);
					a2.setEnabled(false);
					b1.setEnabled(false);
					b2.setEnabled(false);
					b3.setEnabled(false);
					b4.setEnabled(false);
					b5.setEnabled(false);
					c.setEnabled(false);

				} else {

					a1.setEnabled(true);
					a2.setEnabled(true);
					b1.setEnabled(true);
					b2.setEnabled(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
					b5.setEnabled(true);
					c.setEnabled(true);
				}
			}

		});
		lst = new LinguagemDataSource(getActivity());
		text = (AutoCompleteTextView) getActivity()
				.findViewById(R.id.editText1);
		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.e(TAG, "User input: " + s);

				// query the database based on the user input
				item = getItemsFromDb(s.toString());

				// update the adapater
				adp1.notifyDataSetChanged();
				adp1 = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_activated_1, item);
				text.setAdapter(adp1);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		adp1 = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, item);
		text.setAdapter(adp1);

		text.setInputType(InputType.TYPE_TEXT_VARIATION_URI); // optional -
		// sets the
		// keyboard
		// to URL
		// mode

		// kill keyboard when enter is pressed
		text.setOnKeyListener(new OnKeyListener() {
			/**
			 * This listens for the user to press the enter button on the
			 * keyboard and then hides the virtual keyboard
			 */
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (arg1 == KeyEvent.KEYCODE_ENTER)) {
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(
									getActivity().INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
					if (!text.getText().toString().equals("")) {
						FragmentManager fragmentManager = null;
						ListaTodosFragment fragment = null;

						ArrayList<String> lista = new ArrayList<String>();

						if (todos.isChecked()) {
							lista.add(a1.getText().toString());
							lista.add(a2.getText().toString());
							lista.add(b1.getText().toString());
							lista.add(b2.getText().toString());
							lista.add(b3.getText().toString());
							lista.add(b4.getText().toString());
							lista.add(b5.getText().toString());
							lista.add(c.getText().toString());

						} else {
							if (a1.isChecked())
								lista.add(a1.getText().toString());
							if (a2.isChecked())
								lista.add(a2.getText().toString());
							if (b1.isChecked())
								lista.add(b1.getText().toString());
							if (b2.isChecked())
								lista.add(b2.getText().toString());
							if (b3.isChecked())
								lista.add(b3.getText().toString());
							if (b4.isChecked())
								lista.add(b4.getText().toString());
							if (b5.isChecked())
								lista.add(b5.getText().toString());
							if (c.isChecked())
								lista.add(c.getText().toString());
						}
						String result = savebd(lista);

						String titulo = text.getText().toString();
						String area;

						area = sp.getSelectedItem().toString();

						Log.i("CATEGORIA", "fragment:" + titulo);
						Log.i("CATEGORIA", "fragment:" + area);
						LinguagemDataSource source = new LinguagemDataSource(
								getActivity());
						fragment = new ListaTodosFragment(titulo, area, result,
								4);
						source.inserirHistorico2(titulo.trim(), "titulo",
								area.trim(), result);

						fragmentManager = getFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.frame_container, fragment)
								.addToBackStack("tag2").commit();

					} else {

						AlertDialog.Builder alerta = new AlertDialog.Builder(
								getActivity());
						alerta.setIcon(R.drawable.ic_title4);
						alerta.setTitle("Título Invalido");
						alerta.setMessage("Digite um Título valido ! ");
						alerta.show();

					}

					return true;
				}
				return false;
			}

		});

		Button but = (Button) getActivity().findViewById(R.id.button1);
		but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!text.getText().toString().equals("")) {
					FragmentManager fragmentManager = null;
					ListaTodosFragment fragment = null;

					ArrayList<String> lista = new ArrayList<String>();

					if (todos.isChecked()) {
						lista.add(a1.getText().toString());
						lista.add(a2.getText().toString());
						lista.add(b1.getText().toString());
						lista.add(b2.getText().toString());
						lista.add(b3.getText().toString());
						lista.add(b4.getText().toString());
						lista.add(b5.getText().toString());
						lista.add(c.getText().toString());

					} else {
						if (a1.isChecked())
							lista.add(a1.getText().toString());
						if (a2.isChecked())
							lista.add(a2.getText().toString());
						if (b1.isChecked())
							lista.add(b1.getText().toString());
						if (b2.isChecked())
							lista.add(b2.getText().toString());
						if (b3.isChecked())
							lista.add(b3.getText().toString());
						if (b4.isChecked())
							lista.add(b4.getText().toString());
						if (b5.isChecked())
							lista.add(b5.getText().toString());
						if (c.isChecked())
							lista.add(c.getText().toString());
					}
					String result = savebd(lista);

					String titulo = text.getText().toString();
					String area;

					area = sp.getSelectedItem().toString();

					Log.i("CATEGORIA", "fragment:" + titulo);
					Log.i("CATEGORIA", "fragment:" + area);
					LinguagemDataSource source = new LinguagemDataSource(
							getActivity());
					fragment = new ListaTodosFragment(titulo, area, result, 4);
					source.inserirHistorico2(titulo.trim(), "titulo",
							area.trim(), result);

					fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment)
							.addToBackStack("tag2").commit();

				} else {

					AlertDialog.Builder alerta = new AlertDialog.Builder(
							getActivity());
					alerta.setIcon(R.drawable.ic_title4);
					alerta.setTitle("Título Invalido");
					alerta.setMessage("Digite um Título valido ! ");
					alerta.show();

				}
			}

		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(
				R.layout.fragment_titulo_area_estrato3, container, false);
		// ((RadioGroup) rootView.findViewById(R.id.radioGroup1))
		// .setOnCheckedChangeListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// String numeral = null;
		// switch (checkedId) {
		// case R.id.radio_A1:
		// numeral = "first";
		// break;
		// case R.id.radio_A2:
		// numeral = "second";
		// break;
		// case R.id.radio_B1:
		// numeral = "third";
		// break;
		// }
		// Toast.makeText(getActivity().getApplicationContext(),
		// "You selected the "+numeral+" radio button.",
		// Toast.LENGTH_SHORT).show();
	}

	public String savebd(ArrayList<String> lista) {
		String query;
		String query1 = "";
		int i = lista.size() - 1;
		while (i >= 0) {
			String a = lista.get(i);
			i--;
			if (query1.equals("")) {
				query1 = "'" + a + "'";
			} else {
				query1 = query1 + ",'" + a + "'";

			}
		}
		return "(" + query1 + ")";

	}

	public String[] getItemsFromDb(String titulo) {

		// add items on the array dynamically
		List<MyObject> products = lst.allLinguagens(titulo);
		int rowCount = products.size();

		String[] item = new String[rowCount];
		int x = 0;

		for (MyObject record : products) {

			item[x] = record.objectName;
			x++;
		}

		return item;
	}
}
