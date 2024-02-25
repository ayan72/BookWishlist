package com.example.aabbas_mybookwishlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
/*
It is a dialog fragment used to add or edit books in the
list. It implements both edit and add functionality using
the arguments entered to create a new instance.
 */
public class AddBookFragment extends DialogFragment {
    private CheckBox checkStatus;
    private EditText editYear;
    private EditText editTitle;
    private EditText editAuthor;
    private EditText editGenre;

    private AddBookDialogListener listener;
    private Book book;
    private int position = -1;
    public interface AddBookDialogListener{
        void addBook(Book book);
        void editBook(Book book, int position);
    }

    //https://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
    // OpenAI, 2024, ChatGPT, create a new fragment using newInstance
    //function to create a new instance of the fragment
    public static AddBookFragment newInstance(Book book, int position) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddBookDialogListener) {
            listener = (AddBookDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddBookDialogListener");
        }
    }

    //CMPUT301, Lab 3
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_book, null);
        //find the view of each component
        editTitle = view.findViewById(R.id.editTitle);
        editAuthor = view.findViewById(R.id.editAuthor);
        editGenre = view.findViewById(R.id.editGenre);
        editYear = view.findViewById(R.id.editYear);
        checkStatus = view.findViewById(R.id.editStatus);

        if (getArguments() != null) {
            // handle edit
            book = (Book) getArguments().getSerializable("book");
            position = getArguments().getInt("position");
            if (book != null) {
                //set the details of the book being edited
                editTitle.setText(book.getTitle());
                editAuthor.setText(book.getAuthor());
                editGenre.setText(book.getGenre());
                editYear.setText(String.valueOf(book.getYear()));
                checkStatus.setChecked(book.isRead());
            }
        }

        // OpenAI, 2024, ChatGPT, implement edit and add functionality
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        if (book == null) {
            builder.setTitle("Add a Book");
        } else {
            builder.setTitle("Edit Book Details");
        }
        builder.setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    //get the input data in case of add or edit
                    String title = editTitle.getText().toString();
                    String author = editAuthor.getText().toString();
                    String genre = editGenre.getText().toString();
                    // https://www.tutorialspoint.com/java/number_parseint.htm
                    int year = Integer.parseInt(editYear.getText().toString());
                    boolean readStatus = checkStatus.isChecked();

                    //if the book is not present in the wishlist add
                    if (book == null){
                        book = new Book(title, author, genre, year, readStatus);
                        listener.addBook(book);
                    }
                    else{
                        //edit the book
                        book.setTitle(title);
                        book.setAuthor(author);
                        book.setGenre(genre);
                        book.setYear(year);
                        book.setRead(readStatus);
                        listener.editBook(book, position);
                    }
                });
        return builder.create();
    }
}

