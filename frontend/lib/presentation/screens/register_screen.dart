import 'package:bookie/config/constants/general.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  bool _isLoading = false; // Para mostrar el indicador de carga

  // Función para hacer la llamada de registro
  Future<void> _register() async {
    if (_formKey.currentState?.validate() ?? false) {
      setState(() {
        _isLoading = true;
      });

      final name = _nameController.text;
      final email = _emailController.text;
      final password = _passwordController.text;

      try {
        // Simulamos el proceso de registro
        await Future.delayed(Duration(seconds: 2));

        if (name.isNotEmpty && email.isNotEmpty && password.isNotEmpty) {
          // Mostrar un mensaje de éxito
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('¡Registro exitoso!'),
              backgroundColor: Colors.green,
              duration: Duration(seconds: 2),
            ),
          );

          // Esperar un poco para mostrar el efecto y luego navegar a /home/0
          await Future.delayed(Duration(milliseconds: 500));
          if (mounted) {
            context.go('/home/0');
          }
        } else {
          // Si hubo un error, mostramos un mensaje en rojo
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Error: Por favor, comprueba tus datos'),
              backgroundColor: Colors.red,
              duration: Duration(seconds: 2),
            ),
          );
        }
      } catch (e) {
        // Si ocurre algún error, mostramos un mensaje de error
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error: No se pudo registrar'),
            backgroundColor: Colors.red,
            duration: Duration(seconds: 2),
          ),
        );
      } finally {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;
    final isDarkmode = Theme.of(context).brightness == Brightness.dark;

    return Scaffold(
      appBar: AppBar(
        title: Text("Crear cuenta",
            style: TextStyle(color: isDarkmode ? Colors.black : Colors.white)),
        backgroundColor: colors.primary, // Icono de la barra de navegación
      ),
      body: Stack(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Center(
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    ClipRRect(
                      borderRadius:
                          BorderRadius.circular(16), // Bordes redondeados
                      child: Image.asset(
                        GeneralConstants.logo, // Cambia por la URL de tu imagen
                        width: 150, // Ancho de la imagen
                        height: 150, // Alto de la imagen
                        fit: BoxFit.cover,
                      ),
                    ),
                    SizedBox(height: 20),
                    Form(
                      key: _formKey,
                      child: Column(
                        children: [
                          // Name input
                          TextFormField(
                            controller: _nameController,
                            decoration: InputDecoration(
                              labelText: "Nombre completo",
                              prefixIcon: Icon(Icons.person),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                              ),
                            ),
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Por favor ingrese su nombre';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 16),

                          // Email input
                          TextFormField(
                            controller: _emailController,
                            decoration: InputDecoration(
                              labelText: "Correo electrónico",
                              prefixIcon: Icon(Icons.email),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                              ),
                            ),
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Por favor ingrese su correo';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 16),

                          // Password input
                          TextFormField(
                            controller: _passwordController,
                            obscureText: true,
                            decoration: InputDecoration(
                              labelText: "Contraseña",
                              prefixIcon: Icon(Icons.lock),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                              ),
                            ),
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Por favor ingrese su contraseña';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 24),

                          // Register Button
                          ElevatedButton(
                            onPressed: _isLoading
                                ? null
                                : _register, // Deshabilitar el botón durante el registro
                            style: ElevatedButton.styleFrom(
                              minimumSize: Size(double.infinity, 50),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(12),
                              ),
                              backgroundColor: colors.primary,
                            ),
                            child: Text("Crear cuenta",
                                style: TextStyle(
                                    fontSize: 18,
                                    color: isDarkmode
                                        ? Colors.black
                                        : Colors.white)),
                          ),
                        ],
                      ),
                    ),
                    SizedBox(height: 20),
                    // Switch to Login page
                    TextButton(
                      onPressed: () {
                        Navigator.pop(context);
                      },
                      child: Text(
                        "¿Ya tienes cuenta? Inicia sesión aquí",
                        style: TextStyle(color: colors.primary),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),

          // Cargar el loader encima del formulario
          if (_isLoading)
            Positioned.fill(
              child: Container(
                color: Colors.black.withOpacity(
                    0.5), // Fondo oscuro para hacer opaco el formulario
                child: Center(
                  child: CircularProgressIndicator(), // Loader
                ),
              ),
            ),
        ],
      ),
    );
  }
}
