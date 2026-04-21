from kivymd.uix.screen import MDScreen
from kivy.lang import Builder
from kivy.properties import StringProperty

KV_TEXT_TRANS = '''
<TextTranslationScreen>:
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "10dp"
        spacing: "10dp"

        # زر اختيار اللغة (100 لغة) في المنتصف العلوي
        MDRaisedButton:
            id: lang_selector
            text: "إختر اللغة (100 لغة)"
            pos_hint: {"center_x": .5}
            size_hint_x: 0.8
            md_bg_color: [0.2, 0.2, 0.2, 1]
            on_release: root.open_lang_menu()

        # المربع العلوي: المحرر (Input)
        MDCard:
            size_hint_y: 0.4
            md_bg_color: [0.1, 0.1, 0.1, 1]
            padding: "5dp"
            radius: 15
            
            MDBoxLayout:
                orientation: 'vertical'
                MDTextField:
                    id: input_editor
                    hint_text: "اكتب هنا أو استخدم المايك..."
                    multiline: True
                    mode: "fill"
                    fill_color_normal: [0, 0, 0, 0]
                    on_text: root.check_auto_clear()
                
                MDIconButton:
                    icon: "microphone"
                    pos_hint: {"left": 1}
                    theme_icon_color: "Custom"
                    icon_color: [0.6, 0.1, 0.1, 1]
                    on_release: root.start_speech_to_text()

        # المربع السفلي: الترجمة (Output)
        MDCard:
            size_hint_y: 0.4
            md_bg_color: [0.15, 0.15, 0.15, 1]
            padding: "10dp"
            radius: 15
            
            MDBoxLayout:
                orientation: 'vertical'
                MDLabel:
                    id: output_text
                    text: "الترجمة ستظهر هنا..."
                    halign: "right"
                    theme_text_color: "Custom"
                    text_color: [1, 1, 1, 1]

                MDBoxLayout:
                    adaptive_height: True
                    spacing: "10dp"
                    anchor_x: 'right'
                    
                    MDIconButton:
                        icon: "content-copy"
                        on_release: root.copy_text()
                    MDIconButton:
                        icon: "share-variant"
                        on_release: root.share_audio()
                    MDIconButton:
                        icon: "volume-high"
                        on_release: root.play_translation()

class TextTranslationScreen(MDScreen):
    def open_lang_menu(self):
        print("فتح قائمة الـ 100 لغة...")

    def start_speech_to_text(self):
        print("المايك يعمل.. تحويل الصوت لنص داخل المحرر...")
        # هنا النص هينزل في الـ input_editor والمستخدم يعدله براحته

    def check_auto_clear(self):
        # منطقك: إذا بدأ المستخدم يكتب بعد انتهاء ترجمة قديمة يتم المسح
        pass

    def play_translation(self):
        print("نطق الجملة بصوت أدهم...")

    def share_audio(self):
        message = "تمت الترجمة بواسطة ميرور سكربيون"
        print(f"مشاركة ملف الصوت مع إضافة الجملة: {message}")

    def copy_text(self):
        print("تم نسخ النص المترجم")
