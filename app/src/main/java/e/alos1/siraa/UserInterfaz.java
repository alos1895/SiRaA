package e.alos1.siraa;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class UserInterfaz extends AppCompatActivity {
    //1)
    Button IdDesconectar;
    ImageView ivG,ivF,ivI,ivL,ivR,ivH,ivB,ivJ,idPitar;
    TextView estadoConexion;
    TextView tvEstadoEstacionado;
    //-------------------------------------------
    boolean estaPresionado = false;
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    private OutputStream outputStream;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String para la direccion MAC
    private static String address = null;
    //-------------------------------------------
    String estadoEstacionado = "nada";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2)
        //Enlaza los controles con sus respectivas vistas

        IdDesconectar = (Button) findViewById(R.id.idDesconectar);
        idPitar= (ImageView) findViewById(R.id.idPitar);
        ivG=(ImageView)findViewById(R.id.ivG);
        ivF=(ImageView)findViewById(R.id.ivF);
        ivI=(ImageView)findViewById(R.id.ivI);
        ivL=(ImageView)findViewById(R.id.ivL);
        ivR=(ImageView)findViewById(R.id.ivR);
        ivH=(ImageView)findViewById(R.id.ivH);
        ivB=(ImageView)findViewById(R.id.ivB);
        ivJ=(ImageView)findViewById(R.id.ivJ);
        estadoConexion= (TextView)findViewById(R.id.tvEstadoConexion);
        tvEstadoEstacionado= (TextView)findViewById(R.id.tvEstadoEstacionado);
        tvEstadoEstacionado.setText("No estas estacionado");

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter(); // get Bluetooth adapter
        VerificarEstadoBT();

        // Configuracion onClick listeners para los botones
        // para indicar que se realizara cuando se detecte
        // el evento de Click

//-------------------------------Eventos para Enviar-------------------------------------------------------------------------------------------------------------

        ivG.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("l");
                            ivG.setImageResource(R.drawable.g_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        MyConexionBT.write("s");
                        ivG.setImageResource(R.drawable.g);
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("F");
                            ivF.setImageResource(R.drawable.f_presion);

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivF.setImageResource(R.drawable.f);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivI.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("f");
                            ivI.setImageResource(R.drawable.i_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivI.setImageResource(R.drawable.i);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("L");
                            ivL.setImageResource(R.drawable.l_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivL.setImageResource(R.drawable.l);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivR.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("R");
                            ivR.setImageResource(R.drawable.r_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivR.setImageResource(R.drawable.r);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivH.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("b");
                            ivH.setImageResource(R.drawable.h_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivH.setImageResource(R.drawable.h);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("B");
                            ivB.setImageResource(R.drawable.b_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivB.setImageResource(R.drawable.b);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        ivJ.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            estadoEstacionado="nada";
                            esperarMilisegundos("r");
                            ivJ.setImageResource(R.drawable.j_presion);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        ivJ.setImageResource(R.drawable.j);
                        MyConexionBT.write("s");
                        tvEstadoEstacionado.setText("No estas estacionado");
                }
                return true;
            }

        });
        idPitar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!estaPresionado) {
                            estaPresionado = true;
                            esperarMilisegundos("V");
                            idPitar.setImageResource(R.drawable.sonar_presion);

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        estaPresionado = false;
                        MyConexionBT.write("v");
                        idPitar.setImageResource(R.drawable.sonar);
                }
                return true;
            }

        });

        /*
        IdApagar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });*/
//-------------------------------Eventos para Desconectar-------------------------------------------------------------------------------------------------------------
        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btSocket!=null)
                {
                    try {btSocket.close();}
                    catch (IOException e)
                    { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();;}
                }
                finish();
            }
        });
    }

    public void esperarMilisegundos(final String letra) {
        if (estaPresionado==true){
        MyConexionBT.write(letra);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                esperarMilisegundos(letra);
            }
        }, 50);
    }
    }
    public void bi(View v){//bateria izquierda
        if (estadoEstacionado=="nada"){
        MyConexionBT.write("A");
        estadoEstacionado="A";
        tvEstadoEstacionado.setText("Estacionado");}
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }
    }
    public void ci(View v){//cordon izquierda
        if (estadoEstacionado=="nada"){
            MyConexionBT.write("E");
            estadoEstacionado="E";
            tvEstadoEstacionado.setText("Estacionado");}
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }
    }
    public void di(View v){//diagonal izquierda
        if (estadoEstacionado=="nada"){
            MyConexionBT.write("G");
            estadoEstacionado="G";
            tvEstadoEstacionado.setText("Estacionado");}
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }
    }
    public void bd(View v){//Bateria derecha
        if (estadoEstacionado=="nada"){
            MyConexionBT.write("C");
            estadoEstacionado="C";
            tvEstadoEstacionado.setText("Estacionado");}
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }
    }
    public void cd(View v){//Cordon derecha

        if (estadoEstacionado=="nada"){
            MyConexionBT.write("H");
            estadoEstacionado="H";
            tvEstadoEstacionado.setText("Estacionado");}
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }
    }
    public void dd(View v){//diagonal derecha
        if (estadoEstacionado=="nada") {
            MyConexionBT.write("J");
            estadoEstacionado = "J";
            tvEstadoEstacionado.setText("Estacionado");
        }
        else{
            Toast.makeText(getBaseContext(), "Estas estacionado", Toast.LENGTH_LONG).show();
        }}
    public void desaparcar(View v){//desaparcar
        switch (estadoEstacionado) {
            case "A":
                MyConexionBT.write("a");
                break;
            case "E":
                MyConexionBT.write("e");
                break;
            case "G":
                MyConexionBT.write("g");
                break;
            case "C":
                MyConexionBT.write("c");
                break;
            case "H":
                MyConexionBT.write("h");
                break;
            case "J":
                MyConexionBT.write("j");
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "No estas estacionado", Toast.LENGTH_SHORT).show();
                break;
        }
        tvEstadoEstacionado.setText("No estas estacionado");
        estadoEstacionado="nada";
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creaccion del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexi�n con el socket Bluetooth.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        { // Cuando se sale de la aplicaci�n esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }

    //Comprueba que el dispositivo Bluetooth Bluetooth est� disponible y solicita que se active si est� desactivado
    private void VerificarEstadoBT() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
                estadoConexion.setText("Conectado");
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexi�n
                Toast.makeText(getBaseContext(), "La Conexion fallo", Toast.LENGTH_LONG).show();
                estadoConexion.setText("La Conexion fallo");
                finish();
            }
        }
    }
}
