package com.example.aabbas_mybookwishlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
/*
MainActivity serves as an interface for seeing the list of books in the app
and it handles adding and editing of the books by creation of fragments. It also
validates the input entered by the user and tracks the count of books and count of
books read.
 */
public class MainActivity extends AppCompatActivity implements AddBookFragment.AddBookDialogListener {

    private ArrayList<Book> dataList;
    private ListView bookList;
    private BookArrayAdapter bookAdapter;
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://developer.android.com/develop/ui/views/components/appbar/setting-up
        //displays the toolbar with the app name
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataList = new ArrayList<>();
        bookList = findViewById(R.id.list);
        bookAdapter = new BookArrayAdapter(this, dataList);
        bookList.setAdapter(bookAdapter);
        count = findViewById(R.id.counter);

        //deletes the book selected by long click on the list
        https://stackoverflow.com/questions/2558591/remove-listview-items-in-android
        bookList.setOnItemLongClickListener((parent, view, position, id) -> {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete the book")
                            .setMessage("Are you sure you want to delete this book?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dataList.remove(position);
                                            bookAdapter.notifyDataSetChanged();
                                            bookCount();
                                        }
                            })
                            .show();
                    return true;
        });

        //creating a fragment to handle edit of an existing book
        bookList.setOnItemClickListener((parent, view, position, id) -> {
            Book editBook = dataList.get(position);
            AddBookFragment.newInstance(editBook, position).show(getSupportFragmentManager(), "EDIT_BOOK");
        });

        //create a fragment for adding a book when floating action button is pressed
        // https://www.tabnine.com/code/java/methods/android.support.design.widget.FloatingActionButton/setOnClickListener
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v ->
                AddBookFragment.newInstance(null, -1).show(getSupportFragmentManager(), "ADD_BOOK"));
    }
    // OpenAI, 2024, ChatGPT, where to call bookCount() for correct number of books
    //update the counter whenever a new book is added or the status changes to read
    private void bookCount() {
        int totalBooks = dataList.size();
        int read = 0;
        for (Book book : dataList) {
            if (book.isRead()) {
                read++;
            }
        }
        count.setText("Total Books: " + totalBooks + " | Read: " + read);
    }

    //validate the input entered by the user
    //https://developer.android.com/guide/topics/ui/notifiers/toasts#java
    private boolean validateInput(String title, String author, String genre, int year){
        if (title.length()>50||title.isEmpty()){
            Toast.makeText(this, "Title must have between 1 and 50 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (author.length()>30||author.isEmpty()){
            Toast.makeText(this, "Author must have between 1 and 50 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if(genre.isEmpty()){
            Toast.makeText(this, "Specify a genre for the book", Toast.LENGTH_LONG).show();
            return false;
        }

        if(year<1000||year>9999){
            Toast.makeText(this, "Year must be a 4 digit integer", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    //add the book to the wishlist
    @Override
    public void addBook(Book book) {
        if(!validateInput(book.getTitle(),book.getAuthor(), book.getGenre(), book.getYear())){
            return;
        }
        dataList.add(book);
        bookAdapter.notifyDataSetChanged();
        bookCount();
    }

    //edit the selected book in the wishlist
    @Override
    public void editBook(Book book, int position) {
        if(!validateInput(book.getTitle(),book.getAuthor(), book.getGenre(), book.getYear())){
            return;
        }
        if ( position < dataList.size()) {
            dataList.set(position, book);
            bookAdapter.notifyDataSetChanged();
            bookCount();
        }
    }
}
