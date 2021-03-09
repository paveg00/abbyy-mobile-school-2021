package com.example.concurrency;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Button anrButton;
	private final AtomicInteger counter = new AtomicInteger();

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		anrButton = findViewById( R.id.anrButton );
		anrButton.setOnClickListener( this );
		findViewById( R.id.kotlinButton ).setOnClickListener( this );
		findViewById( R.id.handlerAndHandlerButton ).setOnClickListener( this );
		findViewById( R.id.asyncTaskButton ).setOnClickListener( this );
		findViewById( R.id.asyncTaskAndLifecycleButton ).setOnClickListener( this );
		findViewById( R.id.loaderButton ).setOnClickListener( this );
		final Runnable updateText = new Runnable() {
			@Override
			public void run()
			{
				counter.incrementAndGet();
				anrButton.postDelayed( new Runnable() {
					@Override
					public void run()
					{
						anrButton.setText( "Changed text " + counter );
					}
				}, 1000L );
			}
		};
		try {
			sleep(5000L);
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}

		Thread launcherThread = new Thread() {
			@Override
			public void run()
			{
				counter.set( 6 );
				try {
					sleep(2000);
				} catch( InterruptedException e ) {
					e.printStackTrace();
				}
				new Thread(updateText).start();
				try {
					sleep(12000);
				} catch( InterruptedException e ) {
					e.printStackTrace();
				}
				new Thread(updateText).start();
			}
		};
		launcherThread.start();
		HandlerThread handlerThread = new HandlerThread( "background" );
	}

	@Override
	public void onClick( final View v )
	{
		Intent intent;
		int id = v.getId();
		if( id == R.id.anrButton ) {
			intent = AnrActivity.getIntent( this );
		} else if( id == R.id.kotlinButton ) {
			intent = new Intent( this, KotlinActivity.class );
		} else if( id == R.id.handlerAndHandlerButton ) {
			intent = HandlerAndHandlerActivity.getIntent( this );
		} else if( id == R.id.asyncTaskButton ) {
			intent = AsyncTaskActivity.getIntent( this );
		} else {
			throw new IllegalStateException();
		}

		startActivity( intent );
	}
}
