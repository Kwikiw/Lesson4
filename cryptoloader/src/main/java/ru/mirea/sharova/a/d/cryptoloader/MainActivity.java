package ru.mirea.sharova.a.d.cryptoloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import javax.crypto.SecretKey;

import ru.mirea.sharova.a.d.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private ActivityMainBinding binding;
    private final int LoaderID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(this::onClickButton);
    }
    public void onClickButton(View view) {
        String message = binding.editText.getText().toString();

        if (!message.isEmpty()) {
            SecretKey key = CryptoHelper.generateKey();
            byte[] encryptedMessage = CryptoHelper.encryptMsg(message, key);

            Bundle bundle = new Bundle();
            bundle.putByteArray(MyLoader.ARG_WORD, encryptedMessage);
            bundle.putByteArray("key", key.getEncoded());

            LoaderManager.getInstance(this).restartLoader(LoaderID, bundle, this);
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @NonNull Bundle args) {
        if (id == LoaderID) {
            return new MyLoader(this, args);
        }
        throw new IllegalArgumentException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (loader.getId() == LoaderID) {
            if (data != null) {
                Toast.makeText(this, "Decrypted message: " + data, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to decrypt message", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d("MainActivity", "onLoaderReset");
    }

}