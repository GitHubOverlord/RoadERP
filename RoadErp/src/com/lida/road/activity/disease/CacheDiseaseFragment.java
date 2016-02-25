package com.lida.road.activity.disease;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.DiseaseMessageAdapter;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.utils.PersistenceManager;

/**
 * 待审批页面
 * 
 * @author Administrator
 * 
 */
public class CacheDiseaseFragment extends Fragment {
	private View view;
	private List<DiseaseRecord> list;
	private DiseaseMessageAdapter diseaseMessageAdapter;
	private ListView listView;

	public CacheDiseaseFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_disease_cache, container,
				false);
		initView(view);
		return view;

	}

	private void initView(View view) {
		listView = (ListView) view.findViewById(R.id.listview_cache_disease);

	}

	@Override
	public void onResume() {
		super.onResume();
		list = PersistenceManager.getInstance(getActivity())
				.getDiseaseRecords();
		diseaseMessageAdapter = new DiseaseMessageAdapter(getActivity(), list);
		listView.setAdapter(diseaseMessageAdapter);
		listView.setOnItemClickListener(onItemClickListener);
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle = new Bundle();
			bundle.putInt(AddDeseaMessageActivity.Bundle_FROM_MARK,
					AddDeseaMessageActivity.BUNDLE_FROM_CACHE);
			bundle.putSerializable(
					AddDeseaMessageActivity.BUNDLE_DISEASA_MESSAGE,
					list.get(position));
			SystemUtils.intentToAnotherActivity(getActivity(),
					AddDeseaMessageActivity.class, bundle);
		}

	};
}
