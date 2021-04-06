package com.github.ivanshafran.camerasample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.view.CameraController;
import androidx.camera.view.LifecycleCameraController;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraActivity extends AppCompatActivity implements ImageCapture.OnImageSavedCallback {

	private static final int CAMERA_REQUEST_CODE = 0;

	private LifecycleCameraController cameraController;
	private PreviewView cameraView;
	private View takePictureButton;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_camera );

		if( ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA )
			== PackageManager.PERMISSION_GRANTED ) {
			startCamera();
		} else {
			requestPermission();
		}
	}

	private void requestPermission()
	{
		ActivityCompat.requestPermissions(
			this,
			new String[] { Manifest.permission.CAMERA },
			CAMERA_REQUEST_CODE
		);
	}

	@Override
	public void onRequestPermissionsResult(
		final int requestCode,
		@NonNull final String[] permissions,
		@NonNull final int[] grantResults
	)
	{
		if( requestCode == CAMERA_REQUEST_CODE ) {
			if( grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
				startCamera();
			} else {
				if( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.CAMERA ) ) {
					Toast.makeText( this, R.string.need_permission, Toast.LENGTH_SHORT ).show();
				} else {
					Toast.makeText( this, R.string.permission_in_settings, Toast.LENGTH_SHORT ).show();
				}
				finish();
			}
		} else {
			super.onRequestPermissionsResult( requestCode, permissions, grantResults );
		}
	}

	private void startCamera()
	{
		cameraController = new LifecycleCameraController( this );
		cameraController.bindToLifecycle( this );
		cameraController.setEnabledUseCases( CameraController.IMAGE_CAPTURE );
		cameraView = findViewById( R.id.cameraView );
		cameraView.setController( cameraController );

		takePictureButton = findViewById( R.id.takePictureButton );
		takePictureButton.setOnClickListener( v -> {
			ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder( generatePictureFile() ).build();
			cameraController.takePicture(
				options,
				AsyncTask.SERIAL_EXECUTOR,
				CameraActivity.this
			);
		} );
	}

	private File generatePictureFile()
	{
		return new File( getFilesDir(), UUID.randomUUID().toString() );
	}

	@Override
	public void onImageSaved( @NonNull ImageCapture.OutputFileResults outputFileResults )
	{

	}

	@Override
	public void onError( @NonNull ImageCaptureException exception )
	{
		finish();
	}
}
