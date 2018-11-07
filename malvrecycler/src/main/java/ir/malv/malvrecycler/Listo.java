package ir.malv.malvrecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * This class is used to handle all the job.
 */
public class Listo {

    /**
     * We don't want any one to use this class by using `new` keyword.
     * So we make the constructor private and it'll be impossible to build object from.
     */
    private Listo() {
        // Private constructor
    }

    /**
     * This class is the builder of adapter.
     * It's really like:
     * @see android.support.v7.app.AlertDialog.Builder
     * @param <T> is the Model you use for your list.
     */
    public static class Builder<T> {

        private RecyclerView recyclerView;
        private int layoutId = -1;
        private List<T> dataset;
        private RecyclerView.LayoutManager layoutManager;
        private WithEveryItemListener<T> withEveryItemListenerListener;
        private boolean hasFixedSize = false;
        private ListItemClick onClickListener;

        /**
         * Gets the main list that needs adapter
         * @return instance of this class to support cascade (Chaining methods)
         */
        public Builder<T> load(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder<T> withListOf(List<T> dataset) {
            this.dataset = dataset;
            return this;
        }

        public Builder<T> layout(int layout) {
            layoutId = layout;
            return this;
        }

        public Builder<T> setHasFixedSize(boolean hasFixedSize) {
            this.hasFixedSize = hasFixedSize;
            return this;
        }

        public Builder<T> layoutManager(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
            return this;
        }

        public Builder<T> withEveryItem(WithEveryItemListener<T> listener) {
            withEveryItemListenerListener = listener;
            return this;
        }

        public Builder<T> onClick(ListItemClick onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public void build() {
            if (recyclerView == null)
                throw new NullPointerException("You have not passed RecyclerView or it's null, Make sure you call load().");
            if (layoutId == -1) {
                Log.w("Malv Recycler", "No layout id. It'll use android.R.layout.simple_list_item1");
                layoutId = android.R.layout.simple_list_item_1;
            }
            if (layoutManager == null) {
                throw new NullPointerException("No layout manager. Make sure you call layoutManager().");
            }

            recyclerView.setHasFixedSize(hasFixedSize);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new LA<>(dataset, layoutId, withEveryItemListenerListener, onClickListener));

        }
    }

    public interface WithEveryItemListener<T> {
        void doOnCallback(T data, View itemView, int position);
    }

    public static class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }

    public static class LA<T> extends RecyclerView.Adapter<VH> {

        private List<T> dataset;
        private int layout;
        private WithEveryItemListener<T> listener;
        private ListItemClick onClickListener;

        LA(List<T> dataset, int layout, WithEveryItemListener<T> listener, ListItemClick onClickListener) {
            if (dataset == null) {
                throw new NullPointerException("You have passed a null list. Make sure you call withListOf().");
            }
            this.listener = listener;
            this.layout = layout;
            this.dataset = dataset;
            this.onClickListener = onClickListener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            if (listener != null) {
                listener.doOnCallback(dataset.get(position), holder.itemView, position);
            }
            if (onClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }
    }

    public interface ListItemClick {
        void onClick(View view, int position);
    }

}
