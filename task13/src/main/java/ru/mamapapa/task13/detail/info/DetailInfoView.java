package ru.mamapapa.task13.detail.info;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.yandex.dto.Forecast;

/**
 * Представление детальной информации о погоде
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class DetailInfoView {
    private final Activity activity;
    private DetailInfoAdapter adapter;
    private RecyclerView recyclerView;

    public DetailInfoView(Activity activity) {
        this.activity = activity;
        this.adapter = new DetailInfoAdapter();
        initView();
    }

    private void initView() {
        recyclerView = activity.findViewById(R.id.detail_info_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
    }

    public void setPresenter(DetailInfoPresenter presenter) {
        adapter.setPresenter(presenter);
    }

    /**
     * Показать детальную информацию о погоде
     *
     * @param forecast - объект с детальной информацией о погоде
     */
    public void showDetailInfo(Forecast forecast) {
        adapter.addItems(forecast);
    }
}
