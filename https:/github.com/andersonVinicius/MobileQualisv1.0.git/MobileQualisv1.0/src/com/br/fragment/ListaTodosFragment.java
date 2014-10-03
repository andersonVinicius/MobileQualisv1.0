package com.br.fragment;

//import info.androidhive.slidingmenu.DAO.LinguagemDataSource;





import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.activity.R;
import com.br.adapter.LinguagemArrayAdapter;
import com.br.dao.LinguagemDataSource;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaTodosFragment extends ListFragment {

	private static final String CATEGORIA = null;
	private ListView lstLinguagem;
	private List<JSONObject> linguagens;
	private LinguagemArrayAdapter adapter;
	private LinguagemDataSource source;
	private ArrayAdapter<JSONObject> adp;
	private static String param1;
	private static String param2;
	private static String param3;
	private static int a;
	private static String tag;

	public ListaTodosFragment() {

	}

	public ListaTodosFragment(int a) {
		this.a = a;
	}

	public ListaTodosFragment(String param1, int a) {
		this.param1 = param1;
		this.a = a;
	}

	public ListaTodosFragment(String param1, String param2, int a) {
		this.param1 = param1;
		this.param2 = param2;

		this.a = a;

	}

	public ListaTodosFragment(String param1, String param2, String param3, int a) {
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.a = a;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		source = new LinguagemDataSource(getActivity().getApplicationContext());
		switch (a) {

		case 1:
			linguagens = source.allLinguagens();
			

			break;
		case 2:
			// source.inserirHistorico(param1.trim(), "issn");
			Log.i("CATEGORIA :", param1);
			linguagens = source.porIssn(param1.trim());
			
			tag = "O ISSN não é invalido!";
			break;
		case 3:
			linguagens = source.porAreaEstrato(param1.trim(), param2.trim());
			Log.i(CATEGORIA, param2);
			break;
		case 4:

			linguagens = source.porTitulo(param1.trim(), param2.trim(),
					param3.trim());
			
			tag = "A combinação de parâmetros inexistentes para a consulta!";
			break;
		case 5:

			linguagens = source.todosEstratoPorTitulo(param1, param2);
			
			tag = "A combinação de parâmetros inexistentes para a consulta!";
			a = 4;
			break;
		case 6:
			linguagens = source.porAreaTodoEstrato(param1);
			a = 3;
		
		default:
			break;

		}

		if (linguagens.toString() != "[]") {

			lstLinguagem = getListView();
			lstLinguagem.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			lstLinguagem.setMultiChoiceModeListener(new ModeCallback());

			adp = new LinguagemArrayAdapter(getActivity()
					.getApplicationContext(), R.layout.item_lista_todos, linguagens);

			lstLinguagem = (ListView) getListView().findViewById(
					android.R.id.list);
			lstLinguagem.setAdapter(adp);

			lstLinguagem.setAdapter(adp);
		} else {

			AlertDialog.Builder alerta = new AlertDialog.Builder(getListView()
					.getContext());
			alerta.setIcon(R.drawable.ic_title4);
			alerta.setTitle("Busca Invalida");
			alerta.setMessage(tag);
			alerta.show();

			Fragment fragment = null;
			switch (a) {

			case 1:
				// fragment = new PhotosFragment();

				break;
			case 2:
				fragment = new ISSNFragment();

				break;
			case 3:
				fragment = new AreaEstratoFragment();

				break;

			case 4:
				fragment = new TituloAreaEstratoFragment();
				break;
			case 5:
			//	fragment = new WhatsHotFragment();
				break;

			default:
				break;
			}

			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).addToBackStack("tag6").commit();

			}

		}

	}
	
	@Override
	public void onDestroy(){
		
		super.onDestroy();
		source.fechar();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		FragmentActivity activity1 = (FragmentActivity) getActivity();
//		((MainActivity)getActivity()).setOnBackPressedListener(new BaseBackPressedListener(activity1));

		View rootView = inflater.inflate(R.layout.fragment_lista_todos,
				container, false);

		return rootView;
	}

	private class ModeCallback implements ListView.MultiChoiceModeListener {

		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getActivity().getMenuInflater();
			inflater.inflate(R.menu.list_select_menu, menu);
			mode.setTitle("Select Items");
			return true;
		}

		public void shareIt(ArrayList<String> list) {
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			ArrayList<String> shareBody = list;
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Qualis CAPES | PPGEE-UFPA");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"Qualis CAPES | PPGEE-UFPA \n\n Autor :"
							+ getEmail().toString() + "\n\n"
							+ shareBody.toString().trim().replaceAll(",", ""));
			startActivity(Intent.createChooser(sharingIntent,
					"Compartilha via "));

		}

		private String getEmail() {
			try {
				AccountManager accountManager = AccountManager
						.get(getActivity());
				Account[] accounts = accountManager
						.getAccountsByType("com.google");
				if (accounts.length > 0) {
					Account account = accounts[0];
					Toast.makeText(getActivity(), account.name,
							Toast.LENGTH_SHORT).show();
					return account.name;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			SparseBooleanArray checked = getListView()
					.getCheckedItemPositions();
			ArrayList<Integer> positions = new ArrayList<Integer>();

			switch (item.getItemId()) {

			case R.id.share:

				for (int i = 0; i < checked.size(); i++) {
					if (checked.valueAt(i)) {
						positions.add(checked.keyAt(i));
					}
				}

				Collections.sort(positions, Collections.reverseOrder());
				ArrayList<String> list = new ArrayList<String>();
				for (int position : positions) {
					try {
						list.add("======================================================"
								+ "\n");
						list.add(adp.getItem(position).getString("issn")
								.toString()
								+ "\n");
						list.add(adp.getItem(position).getString("titulo")
								.toString()
								+ "\n");
						list.add(adp.getItem(position).getString("area")
								.toString()
								+ "\n");
						list.add(adp.getItem(position).getString("estrato")
								.toString()
								+ "\n");
						list.add("======================================================"
								+ "\n");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				shareIt(list);
				break;

			case R.id.ADD:

				for (int i = 0; i < checked.size(); i++) {
					if (checked.valueAt(i)) {
						positions.add(checked.keyAt(i));
					}
				}

				Collections.sort(positions, Collections.reverseOrder());

				for (int position : positions) {
					try {

						source.addFavoritos(adp.getItem(position).getInt("id"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				Toast.makeText(getActivity(), "Adicionado aos favoritos !",
						Toast.LENGTH_SHORT).show();

				// onActivityCreated(getArguments());

				break;

			default:
				Toast.makeText(getActivity(), "Clicked " + item.getTitle(),
						Toast.LENGTH_SHORT).show();
				break;
			}
			return true;
		}

		public void onDestroyActionMode(ActionMode mode) {
		}

		public void onItemCheckedStateChanged(ActionMode mode, int position,
				long id, boolean checked) {
			final int checkedCount = getListView().getCheckedItemCount();
			switch (checkedCount) {
			case 0:
				mode.setSubtitle(null);
				break;
			case 1:
				mode.setSubtitle("One item selected");
				break;
			default:
				mode.setSubtitle("" + checkedCount + " items selected");
				break;
			}
		}

	}
}
