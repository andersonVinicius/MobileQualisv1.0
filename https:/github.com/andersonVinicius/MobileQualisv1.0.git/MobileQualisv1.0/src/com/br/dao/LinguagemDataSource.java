package com.br.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.br.model.MyObject;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Contacts.Data;
import android.text.method.DateKeyListener;
import android.util.Log;

public class LinguagemDataSource {

	private static final String CATEGORIA = null;
	private SQLiteDatabase db;
	private DatabaseHelper helper; // resposavel pela manuten����o do codgo
	static private Date data;

	public LinguagemDataSource(Context context) {
		helper = new DatabaseHelper(context);
		db = helper.getDatabase();
		data = new Date();

	}

	public List<JSONObject> todosEstratoPorTitulo(String titulo, String area) {

		Log.i("CATEGORIA", "aqui na query : " + titulo);
		Log.i("CATEGORIA", "aqui na query:" + area);
		// Log.i("CATEGORIA", "aqui na query:" + estrato);

		List<JSONObject> result = new ArrayList<JSONObject>();

		Cursor cursor = db.query("tab_todo", new String[] { "id", "issn",
				"titulo", "estrato", "area" }, "titulo LIKE " + "'%" + titulo
				+ "%'AND area LIKE" + "'%" + area + "%'", null, null, null,
				"estrato ASC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));

			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public void addFavoritos(int id) {

		if (verificarFavorito(id) == 0) {
			String sql = "insert into tab_my_todo(id_tab_todo) values" + "("
					+ id + ")";
			db.execSQL(sql);

		} else {

			Log.i(CATEGORIA,
					"##########Os dados ja existem em banco#################################");
		}
	}

	public int verificarFavorito(int id) {
		int count = db.query("tab_my_todo", new String[] { "id" },
				"id_tab_todo =" + id, null, null, null, null).getCount();

		return count;

	}

	public void deleteFavoritos(int id) {
		Log.i(CATEGORIA, "Deletou registro");
		// String sql =
		// "SELECT  titulo FROM tab_todo as a, tab_my_todo as b WHERE a.id=b.id_tab_todo";
		String sql = "delete from tab_my_todo WHERE id_tab_todo =" + id;
		db.execSQL(sql);

		Log.i(CATEGORIA, "Deletou registro");
	}

	public void deleteFavoritosAll() {
		Log.i(CATEGORIA, "Deletou registro");
		// String sql =
		// "SELECT  titulo FROM tab_todo as a, tab_my_todo as b WHERE a.id=b.id_tab_todo";
		String sql = "delete from tab_my_todo ";
		db.execSQL(sql);

		Log.i(CATEGORIA, "Deletou registro");
	}

	public List<JSONObject> exibirFavoritos() {

		List<JSONObject> result = new ArrayList<JSONObject>();
		String sql = "SELECT a.id,a.issn,a.titulo,a.estrato,a.area FROM tab_todo as a , tab_my_todo as b  WHERE a.id=b.id_tab_todo";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));
			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public List<MyObject> allLinguagens(String titulo) {

		List<MyObject> result = new ArrayList<MyObject>();
		String sql = "SELECT titulo FROM tab_todo WHERE titulo LIKE" + "'%"
				+ titulo + "%' ORDER BY id Desc LIMIT 1,5";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			// JSONObject obj = new JSONObject();

			String objectName = cursor.getString(cursor
					.getColumnIndex("titulo"));
			MyObject myObject = new MyObject(objectName);
			// obj.put("titulo", "Título: " + cursor.getString(0));
			result.add(myObject);

			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public List<JSONObject> porIssn(String issn) {

		List<JSONObject> result = new ArrayList<JSONObject>();
		Log.i("CATEGORIA", "aqui na query : " + issn);
		Cursor cursor = db.query("tab_todo", new String[] { "id", "issn",
				"titulo", "estrato", "area" },
				"issn LIKE" + "'%" + issn + "%'", null, null, null,
				"estrato ASC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));
				Log.i("CATEGORIA :", obj.optString("issn", ""));
			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public List<JSONObject> porTitulo(String titulo, String area, String estrato) {

		Log.i("CATEGORIA", "aqui na query : " + titulo);
		Log.i("CATEGORIA", "aqui na query:" + area);
		Log.i("CATEGORIA", "aqui na query:" + estrato);

		List<JSONObject> result = new ArrayList<JSONObject>();

		Cursor cursor = db.query("tab_todo", new String[] { "id", "issn",
				"titulo", "estrato", "area" }, "titulo LIKE " + "'%" + titulo
				+ "%'AND area LIKE" + "'%" + area + "%'AND estrato in "
				+ estrato, null, null, null, "estrato ASC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));

			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public List<JSONObject> porAreaEstrato(String area, String estrato) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		Log.i(CATEGORIA, estrato);
		Cursor cursor = db
				.query("tab_todo", new String[] { "id", "issn", "titulo",
						"estrato", "area" }, "area=" + "'" + area + "'"
						+ " and estrato in " + estrato, null, null, null,
						"estrato ASC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));
			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public List<JSONObject> porAreaTodoEstrato(String area) {
		List<JSONObject> result = new ArrayList<JSONObject>();

		Cursor cursor = db.query("tab_todo", new String[] { "id", "issn",
				"titulo", "estrato", "area" }, "area=" + "'" + area + "'",
				null, null, null, "titulo ASC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("issn", "ISSN: " + cursor.getString(1));
				obj.put("titulo", "Título: " + cursor.getString(2));
				obj.put("estrato", "Estrato: " + cursor.getString(3));
				obj.put("area", "Área: " + cursor.getString(4));
			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToNext();
		}

		cursor.close();
		return result;

	}

	public void inserirHistorico(String parametro, String tipo) {

		String sql = "insert into registro (parametro,tipo) values(" + "'"
				+ parametro + "'" + "," + "'" + tipo + "'" + ")";
		db.execSQL(sql);

	}

	/*
	 * public void inserirHistorico3(String parametro, String tipo, String area)
	 * {
	 * 
	 * Log.i("CATEGORIA", "passou em historico 3"); Log.i("CATEGORIA",
	 * parametro); Log.i("CATEGORIA", tipo); Log.i("CATEGORIA", area); String
	 * estrato="xx"; String sql =
	 * "insert into registro (parametro,tipo,area,estrato) values(" +"'" +
	 * parametro+ "',"+"'" +tipo+ "',"+"'"+ area+ "',"+ "'" +estrato+ "'"+ ")";
	 * db.execSQL(sql);
	 * 
	 * }
	 */

	public void inserirHistorico2(String parametro, String tipo, String area,
			String estrato) {
		Log.i("CATEGORIA", "passou em historico 2");
		String sql = "insert into registro (parametro,tipo,area,estrato) values("
				+ "'"
				+ parametro
				+ "',"
				+ "'"
				+ tipo
				+ "',"
				+ "'"
				+ area
				+ "'," + '"' + estrato + '"' + ")";
		db.execSQL(sql);

	}

	public List<JSONObject> listaHistorico() {
		List<JSONObject> result = new ArrayList<JSONObject>();

		Cursor cursor = db.query("registro", new String[] { "id", "tipo",
				"parametro", "area", "estrato", "data" }, null, null, null,
				null, null);
		cursor.moveToLast();
		while (!cursor.isBeforeFirst()) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", cursor.getString(0));
				obj.put("tipo", cursor.getString(1));
				obj.put("parametro", cursor.getString(2));
				obj.put("area", cursor.getString(3));
				obj.put("estrato", cursor.getString(4));
				obj.put("data", cursor.getString(5));

			} catch (JSONException e) {

			}
			result.add(obj);
			cursor.moveToPrevious();
		}

		cursor.close();
		return result;

	}

	public void deleteHistorico(int id) {
		Log.i(CATEGORIA, "Deletou registro");
		// String sql =
		// "SELECT  titulo FROM tab_todo as a, tab_my_todo as b WHERE a.id=b.id_tab_todo";
		String sql = "delete from registro WHERE id =" + id;
		db.execSQL(sql);

		Log.i(CATEGORIA, "Deletou registro");
	}

	public void fechar() {
		if (db != null) {
			db.close();
		}
	}
}
