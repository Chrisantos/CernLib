package com.example.chriseze.cernlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private SessionManager sessionManager;
    private CardView mCardStudents, mCardBooks, mCardBorrowed, mCardAddBook, mCardAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(mToolbar);
        this.setTitle("CernLib");

        sessionManager = new SessionManager(this);
        sessionManager.setLogin(true);

        mCardBooks = (CardView) findViewById(R.id.view_books);
        mCardBorrowed = (CardView) findViewById(R.id.view_borrowed);
        mCardStudents = (CardView) findViewById(R.id.view_students);
        mCardAddBook = (CardView) findViewById(R.id.add_book);
        mCardAddStudent = (CardView) findViewById(R.id.add_student);

        mCardStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Students.class));
            }
        });
        mCardBorrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Lends.class));
            }
        });
        mCardBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Books.class));
            }
        });
        mCardAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddBook.class));
            }
        });
        mCardAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddStudent.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout){
            new SessionManager(this).logout();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        return true;
    }
}
