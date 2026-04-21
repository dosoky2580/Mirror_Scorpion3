from kivymd.uix.screen import MDScreen
from kivy.lang import Builder

KV_GAMES = '''
<GamesScreen>:
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "20dp"
        spacing: "20dp"

        MDLabel:
            text: "ساحة التحدي"
            halign: "center"
            font_style: "H4"
            theme_text_color: "Custom"
            text_color: [1, 1, 1, 1]
            size_hint_y: None
            height: "50dp"

        # كارت اختيار الشطرنج
        MDCard:
            size_hint_y: None
            height: "150dp"
            radius: 20
            md_bg_color: [0.1, 0.1, 0.1, 1]
            ripple_behavior: True
            on_release: root.start_chess()
            
            MDBoxLayout:
                padding: "15dp"
                spacing: "15dp"
                MDIcon:
                    icon: "chess-knight"
                    font_size: "60sp"
                    theme_text_color: "Custom"
                    text_color: [0.6, 0.1, 0.1, 1]
                MDBoxLayout:
                    orientation: 'vertical'
                    MDLabel:
                        text: "شطرنج 3D"
                        font_style: "H6"
                        theme_text_color: "Custom"
                        text_color: [1, 1, 1, 1]
                    MDLabel:
                        text: "تحدى ذكاء العقرب في مواجهة استراتيجية"
                        font_style: "Caption"
                        theme_text_color: "Secondary"

        # كارت اختيار مكعب روبيك
        MDCard:
            size_hint_y: None
            height: "150dp"
            radius: 20
            md_bg_color: [0.1, 0.1, 0.1, 1]
            ripple_behavior: True
            on_release: root.start_rubiks()
            
            MDBoxLayout:
                padding: "15dp"
                spacing: "15dp"
                MDIcon:
                    icon: "cube-outline"
                    font_size: "60sp"
                    theme_text_color: "Custom"
                    text_color: [0.1, 0.4, 0.8, 1]
                MDBoxLayout:
                    orientation: 'vertical'
                    MDLabel:
                        text: "مكعب روبيك 3D"
                        font_style: "H6"
                        theme_text_color: "Custom"
                        text_color: [1, 1, 1, 1]
                    MDLabel:
                        text: "حل اللغز بجميع الطرق الممكنة"
                        font_style: "Caption"
                        theme_text_color: "Secondary"

        Widget: # فاصل

class GamesScreen(MDScreen):
    def start_chess(self):
        print("جاري تحميل محرك الشطرنج 3D...")
        # هنا هنربط مكتبة chess engine لاحقاً

    def start_rubiks(self):
        print("جاري تجهيز مكعب روبيك والمحاكي...")
