package com.hifeful.libraryippt.features.booklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hifeful.libraryippt.R;
import com.hifeful.libraryippt.data.BookMinimal;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    private ArrayList<BookMinimal> mBooksMinimal;
    private OnBookListener mOnBookListener;

    public interface OnBookListener {
        void onBookClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookAuthor;
        TextView bookCategory;
        OnBookListener onBookListener;

        public ViewHolder(@NonNull View itemView, OnBookListener onBookListener) {
            super(itemView);

            bookName = itemView.findViewById(R.id.list_book_name);
            bookAuthor = itemView.findViewById(R.id.list_book_author);
            bookCategory = itemView.findViewById(R.id.list_book_category);
            this.onBookListener = onBookListener;

            itemView.setOnClickListener(view -> onBookListener.onBookClick(getLayoutPosition()));
        }
    }

    public BookListAdapter(ArrayList<BookMinimal> mBooksMinimal, OnBookListener onBookListener) {
        this.mBooksMinimal = mBooksMinimal;
        this.mOnBookListener = onBookListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_list_item,
                parent, false);

        return new ViewHolder(view, mOnBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(mBooksMinimal.get(position).getName());
        String author = mBooksMinimal.get(position).getAuthorFirstName() + " " +
                mBooksMinimal.get(position).getAuthorLastName();
        holder.bookAuthor.setText(author);
        holder.bookCategory.setText(mBooksMinimal.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return mBooksMinimal.size();
    }
}
