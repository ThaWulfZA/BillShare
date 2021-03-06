package com.ericliu.billshare.dialog;

import static com.ericliu.billshare.provider.DatabaseConstants.*;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_ROWID;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_TYPE;
import static com.ericliu.billshare.provider.DatabaseConstants.COL_PAID;
import android.app.Activity;
import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.ericliu.billshare.R;
import com.ericliu.billshare.provider.BillProvider;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import static com.ericliu.billshare.provider.DatabaseConstants.*;
/*
 * Try to use Loader to load data into Dialog but failed
 */
import static com.ericliu.billshare.provider.DatabaseConstants.*;
public class SelectBillsDialog extends DialogFragment implements OnClickListener, LoaderCallbacks<Cursor> {
	
	
	
	private static final int loaderID = 11;
	private SimpleCursorAdapter adapter;
	private ListView lv;
	private SelectBillsDialogListener mCallback;

	public interface SelectBillsDialogListener{
		void onFinishSelectBills(long[] ids);
	}
	
	private static final String[] PROJECTION = {
		COL_ROWID,
		COL_PAID,
		COL_BILL_NAME
	};
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		Loader<Cursor> loader = activity.getLoaderManager().getLoader(loaderID);
        if (loader != null && !loader.isReset()) {
            activity.getLoaderManager().restartLoader(loaderID, null, this);
        } else {
        	activity.getLoaderManager().initLoader(loaderID, null, this);
        }
		
		try {
			mCallback = (SelectBillsDialogListener) activity;
		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + " must implement SelectBillsDialogListener");
		}

		String[] from = {  COL_BILL_NAME};
		int[] to = { android.R.id.text1  };
		
		adapter = new SimpleCursorAdapter(activity, android.R.layout.simple_list_item_multiple_choice, null,
				from, to, 0) ;
//		{
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				View result = super.getView(position, convertView, parent);
//
//				ViewHolder holder = (ViewHolder) result.getTag();
//
//				if (holder == null) {
//					holder = new ViewHolder();
//					holder.cktv = (CheckedTextView) result
//							.findViewById(R.id.text1);
//					holder.tvChecked = (TextView) result
//							.findViewById(R.id.tvChecked);
//					result.setTag(holder);
//				}
//				
//				if (holder.tvChecked.getText().toString().equals(String.valueOf(1))) {
//					holder.cktv.setChecked(true);
//				}
//
//				return result;
//			}
//		};
		
		
	}
	
	private static class ViewHolder{
		private CheckedTextView cktv;
		private TextView tvChecked;
	}
	
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.select_bills).setPositiveButton(R.string.done, this).setNegativeButton(R.string.cancel, this);
		
		View dialogView = getActivity().getLayoutInflater().inflate(R.layout.multi_choice_listview, null);
		builder.setView(dialogView).setMessage(R.string.paid_bill_not_display);
		
		lv = (ListView) dialogView.findViewById(R.id.lvMulti);
		TextView tvEmpty = (TextView) dialogView.findViewById(R.id.tvEmpty);
		tvEmpty.setText(R.string.you_havent_created_any_bill_yet);
		lv.setAdapter(adapter);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv.setEmptyView(tvEmpty);
		
		
		
		return builder.create();
	}
	
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// respond to action buttons click
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			mCallback.onFinishSelectBills(lv.getCheckedItemIds());
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			
			break;

		default:
			break;
		}
		
		
	}
	
	

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		
		return new CursorLoader(getActivity(), BillProvider.DIALOG_BILL_URI, PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
		for (int i = 0; i < lv.getCount(); i++) {
			lv.setItemChecked(i, true);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}




	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		Bundle data = new Bundle();
	}
	
	
	

}
