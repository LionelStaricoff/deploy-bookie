import 'package:auth0_flutter/auth0_flutter.dart';
import 'package:bookie/config/constants/environment.dart';
import 'package:bookie/config/fetch/fetch_api.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  final auth0 = Auth0(Environment.theAuth0Domain, Environment.theAuth0ClientId);

  Future<void> login(BuildContext context) async {
    try {
      // Invoca el Universal Login
      final credentials = await auth0
          .webAuthentication(
            scheme: 'demo',
          )
          .login();

      // Guardar los credenciales en SharedPreferences

      // prueba para visualizar los datos guardados
      final response = await FetchApi.fetchDio().get(
        '/auth/user',
        options: Options(
          headers: {
            'Authorization': 'Bearer ${credentials.idToken}',
          },
        ),
      );

      print("Datos guardados en SharedPreferences: ${response.data}");

      final prefs = await SharedPreferences.getInstance();
      prefs.setString('id', response.data['id'].toString());
      prefs.setString('name', response.data['name']);
      prefs.setString('email', response.data['email']);
      context.go('/home/0'); // Usando go_route
    } catch (e) {
      print("ERORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: $e");
    }
  }

  Future<void> logout(BuildContext context) async {
    try {
      await auth0
          .webAuthentication(
            scheme: 'demo',
          )
          .logout();

      // Limpiar datos de sesión persistente
      final prefs = await SharedPreferences.getInstance();
      prefs.remove('id');
      prefs.remove('name');
      prefs.remove('email');

      context.go('/splash'); // Redirige a la pantalla de splash/login
    } catch (e) {
      print("ERROOOOOOOOOOOO LOGOUTTTTTTTTTTTTTTTTTTTTT: $e");
    }
  }
}
