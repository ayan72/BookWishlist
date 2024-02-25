package com.example.aabbas_mybookwishlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/*
This class implements a custom array adapter which is responsible for
mapping the book details onto the views. It displays the book and its details
in a custom view defined in content.xml
 */
public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    // CMPUT 301, Lab 3 Instructions
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            //inflate a new view
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        }

        //get the book object for the position
        Book book = getItem(position);

        //Find each view
        TextView bookTitle = view.findViewById(R.id.bookTitle);
        TextView bookAuthor = view.findViewById(R.id.bookAuthor);
        TextView bookGenre = view.findViewById(R.id.bookGenre);
        TextView bookYear = view.findViewById(R.id.bookYear);
        TextView bookStatus = view.findViewById(R.id.bookStatus);

        //set the book data to the view
        assert book != null;
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookGenre.setText(book.getGenre());
        bookYear.setText(String.valueOf(book.getYear()));
        if (book.isRead()) {
            bookStatus.setText("Read");
        } else {
            bookStatus.setText("Unread");
        }

        return view;
    }
}
