# TennisApp 🎾

TennisApp adalah aplikasi Android pendamping bagi pecinta tenis. Aplikasi ini memungkinkan pengguna untuk menghitung skor pertandingan secara real-time, melihat peringkat pemain ATP dunia, memantau jadwal turnamen, dan mempelajari materi teknik tenis.

## ✨ Fitur Utama

- **Authentication**: Sistem Registrasi dan Login untuk menjaga data tetap personal.
- **Match Calculator**: Kalkulator skor tenis (Sets, Games, Points) yang mendukung mode Single/Double dan fitur simpan riwayat.
- **Player Rankings**: Menampilkan peringkat pemain tenis dunia secara real-time menggunakan API eksternal.
- **Tournament Calendar**: Jadwal turnamen tenis internasional.
- **Match History**: Riwayat pertandingan yang tersimpan secara lokal dan dipisahkan antar akun.
- **Materi Tenis**: Daftar panduan teknik bermain tenis dengan detail yang menarik.
- **Dark Mode**: Dukungan tema gelap dan terang untuk kenyamanan mata.

## 🛠️ Implementasi Teknis

Aplikasi ini dibangun dengan standar pengembangan Android modern:
- **Activity & Fragment**: Menggunakan minimal 5 Activity dan 5 Fragment.
- **Navigation Component**: Mengelola perpindahan antar Fragment dengan lebih terstruktur melalui `nav_graph`.
- **Retrofit & Gson**: Mengambil dan mengolah data dari RapidAPI (Tennis ATP/WTA API).
- **SQLite (Local Database)**: Menyimpan data match history, ranking, dan turnamen agar aplikasi tetap bisa diakses saat offline.
- **SharedPreferences**: Mengelola session login user dan pengaturan tema aplikasi.
- **Background Thread**: Menjalankan operasi database berat di latar belakang agar UI tetap lancar.
- **Glide**: Library untuk loading gambar secara efisien.

## 📚 Library yang Digunakan

- [Retrofit](https://square.github.io/retrofit/) - Networking
- [Gson](https://github.com/google/gson) - JSON Parser
- [Glide](https://github.com/bumptech/glide) - Image Loading
- [Navigation Component](https://developer.android.com/guide/navigation) - Navigation
- [OkHttp](https://square.github.io/okhttp/) - HTTP Client & Logging Interceptor

## 🚀 Cara Menjalankan

1. Clone repository ini.
2. Buka project di Android Studio (Koala atau versi terbaru).
3. Pastikan perangkat/emulator terhubung dengan internet untuk pengambilan data API pertama kali.
4. Lakukan Registrasi akun, kemudian Login.
5. Selamat menggunakan TennisApp!
