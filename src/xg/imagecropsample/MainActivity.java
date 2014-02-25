package xg.imagecropsample;

import java.io.FileNotFoundException;
import java.io.InputStream;

import xg.imagecrop.ImageCropView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageCropView mImageCutView = null;
	
	private DisplayMetrics mMetrics = new DisplayMetrics();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
		
		mImageCutView = (ImageCropView) findViewById(R.id.icv_imageCutView);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		InputStream is = getResources().openRawResource(R.raw.liushishi);
		BitmapFactory.decodeStream(is, null, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = options.outWidth / mMetrics.widthPixels;
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		mImageCutView.setImageBitmap(bitmap);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_crop:
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				try {
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image.jpg";
					mImageCutView.cropImageBitmap(path);
					Toast.makeText(this, "已保存至" + path, Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException ex){
					ex.printStackTrace();
					Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "请插入Sdcard", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
