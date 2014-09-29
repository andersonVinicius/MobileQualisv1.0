package com.br.fragment;

import java.util.ArrayList;

import com.br.activity.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AreaEstratoFragment extends Fragment implements
		OnItemClickListener, OnCheckedChangeListener {

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

	private Spinner sp = null;
	CheckBox a1, a2, b1, b2, b3, b4, b5, c, todos;

	public AreaEstratoFragment() {
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

		// final RadioGroup botaoRG = (RadioGroup) getActivity().findViewById(
		// R.id.radioGroup1);
		Button but = (Button) getActivity().findViewById(R.id.button1);
		but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

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

				FragmentManager fragmentManager = null;
				ListaTodosFragment fragment = null;

				String area;

				area = sp.getSelectedItem().toString();

				Log.i("CATEGORIA", "fragment:" + area);

				fragment = new ListaTodosFragment(area, result, 3);

				fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment)
						.addToBackStack("tag4").commit();

			}

		});

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_area_estrato3,
				container, false);
		// ((RadioGroup) rootView.findViewById(R.id.radioGroup1))
		// .setOnCheckedChangeListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

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

}