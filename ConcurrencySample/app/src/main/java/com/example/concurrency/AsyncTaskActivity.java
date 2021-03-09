package com.example.concurrency;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncTaskActivity extends AppCompatActivity {

	public static Intent getIntent( @NonNull final Context context )
	{
		return new Intent( context, AsyncTaskActivity.class );
	}

	private ProgressBar progressBar;
	private TextView textView;
	private AsyncTask<String, Void, Integer> asyncTask;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.sample );

		progressBar = findViewById( R.id.progressBar );
		textView = findViewById( R.id.textView );
	}

	@SuppressLint( "StaticFieldLeak" )
	@Override
	protected void onResume()
	{
		super.onResume();
		asyncTask = new AsyncTask<String, Void, Integer>() {
			@Override
			@WorkerThread
			protected Integer doInBackground( String... strings )
			{
				Log.d("Debug", "this is in background " + Thread.currentThread().getId());
				for (String string : strings) {
					Log.d( "Debug", string );
				}
				return 53;
			}

			@Override
			@MainThread
			protected void onPreExecute()
			{
				super.onPreExecute();
			}

			@Override
			@MainThread
			protected void onPostExecute( Integer integer )
			{
				super.onPostExecute( integer );
				Log.d( "Debug", "Result: " + integer + ": " + Thread.currentThread().getId() );
			}

			@Override
			@MainThread
			protected void onProgressUpdate( Void... values )
			{
				super.onProgressUpdate( values );
			}
		}.execute( "1", "2", "3" );
		/*
		asyncTask = new AsyncTask<String, Void, Integer>() {

			@WorkerThread
			@Override
			protected Integer doInBackground( final String... voids )
			{
				return TheMostImportantQuestionSolver.solve( 5 );
			}

			@MainThread
			@Override
			protected void onPostExecute( final Integer integer )
			{
				super.onPostExecute( integer );

				textView.setText( "Answer = " + integer );
				progressBar.setVisibility( View.INVISIBLE );
			}
		}.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR );
		*/
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if( asyncTask != null ) {
			asyncTask.cancel( false );
			asyncTask = null;
		}
	}
}
