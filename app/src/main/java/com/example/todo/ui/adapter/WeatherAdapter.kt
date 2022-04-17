package com.example.todo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @ProjectName:    ToDo
 * @Package:        com.example.todo.ui.adapter
 * @ClassName:      WeatherAdapter
 * @Author:         Yan
 * @CreateDate:     2022年04月16日 22:47:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class WeatherAdapter(val context:Context,val typeList : ArrayList<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    companion object{

        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE= 3
        const val TYPE_FOUR = 4

    }


    override fun getItemViewType(position: Int): Int {

        when(position){
            1 -> {
                return TYPE_ONE
            }
            2 -> {
                return TYPE_TWO
            }
            3 -> {
                return TYPE_THREE
            }
            4 -> {
                return TYPE_FOUR
            }

        }

        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            TYPE_ONE->{

            }
            TYPE_TWO->{

            }
            TYPE_THREE->{

            }
            TYPE_FOUR->{

            }
        }
        TODO("return view")

//        if (viewType == TYPE_THREE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3, parent, false)
//            ThreeViewHolder threeViewHolder = new ThreeViewHolder(view)
//            return threeViewHolder
//        } else if (viewType == TYPE_TWO) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
//            TwoViewHolder recyclerViewHolder = new TwoViewHolder(view);
//            return recyclerViewHolder;
//        } else if (viewType == TYPE_ONE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);
//            OneViewHolder recycleViewHolderOne = new OneViewHolder(view);
//            return recycleViewHolderOne;
//        } else if (viewType == TYPE_FOUR) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item4, parent, false);
//            FourViewHolder recycleViewHolderFour = new FourViewHolder(view);
//            return recycleViewHolderFour;
//        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_ONE->{
                return
            }
            TYPE_TWO->{

            }
            TYPE_THREE->{

            }
            TYPE_FOUR->{

            }

        }

        TODO("different view")


//        case TYPE_ONE:
//        break;
//
//        case TYPE_TWO:
//        TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
//
//        twoViewHolder.banner.setAdapter(new ImageAdapter(DataBean.getTestData3(),context));
//
//        twoViewHolder.banner.setIndicator(new CircleIndicator(context));
//        twoViewHolder. banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(Object data, int position) {
//                Toast.makeText(context, "点击" + position, Toast.LENGTH_SHORT).show();
//
//                context.startActivity(new Intent(context, Main2Activity.class));
//
//            }
//            @Override
//            public void onBannerChanged(int position) {
//
//            }
//        });
//        twoViewHolder.banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
//        twoViewHolder.banner.setOrientation(Banner.VERTICAL);
//        twoViewHolder.banner.start();
//
//        break;
//
//        case TYPE_THREE:
//
//        ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
//
//        Videoadapter videoadapter=new Videoadapter(context);
//        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        threeViewHolder.rv_3.setLayoutManager(staggeredGridLayoutManager);
//        threeViewHolder.rv_3.setAdapter(videoadapter);
//
//        break;
//
//        case TYPE_FOUR:
//        List<SwipeCardBean> mDatas = SwipeCardBean.initDatas();
//        FourViewHolder fourViewHolder = (FourViewHolder) holder;
//        fourViewHolder.recyclerView.setLayoutManager(new OverLayCardLayoutManager());
//        Myadapter myadapter=new Myadapter( mDatas,context);
//        fourViewHolder.recyclerView.setAdapter(myadapter);
//        CardConfig.initConfig(context);
//        ItemTouchHelper.Callback callback = new RenRenCallback(fourViewHolder.recyclerView, myadapter, mDatas);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(fourViewHolder.recyclerView);
//
//        break;

    }

    override fun getItemCount(): Int {
        return typeList.size
    }

}