from kivymd.uix.screen import MDScreen
from kivy.lang import Builder
from kivymd.uix.dialog import MDDialog
from kivymd.uix.button import MDFlatButton

KV_SETTINGS = '''
<SettingsScreen>:
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "15dp"
        spacing: "10dp"

        MDLabel:
            text: "الإعدادات"
            halign: "center"
            font_style: "H4"
            theme_text_color: "Custom"
            text_color: [1, 1, 1, 1]
            size_hint_y: None
            height: "60dp"

        MDScrollView:
            MDList:
                # الوضع المظلم
                OneLineIconListItem:
                    text: "الوضع المظلم (Dark Mode)"
                    theme_text_color: "Custom"
                    text_color: [1, 1, 1, 1]
                    on_release: root.toggle_dark_mode()
                    IconLeftWidget:
                        icon: "brightness-6"

                # الترقية للنسخة المدفوعة
                OneLineIconListItem:
                    text: "الترقية للنسخة المدفوعة"
                    theme_text_color: "Custom"
                    text_color: [1, 0.8, 0, 1] # لون ذهبي للتميز
                    on_release: root.show_upgrade_info()
                    IconLeftWidget:
                        icon: "crown"
                        theme_icon_color: "Custom"
                        icon_color: [1, 0.8, 0, 1]

                # نبذة عن التطبيق (فلسفة تامر)
                OneLineIconListItem:
                    text: "نبذة عن ميرور سكربيون"
                    theme_text_color: "Custom"
                    text_color: [1, 1, 1, 1]
                    on_release: root.show_about_dialog()
                    IconLeftWidget:
                        icon: "information-outline"

                # المطور
                TwoLineIconListItem:
                    text: "المطور"
                    secondary_text: "TetoCollctionWay"
                    theme_text_color: "Custom"
                    text_color: [1, 1, 1, 1]
                    IconLeftWidget:
                        icon: "code-braces"
'''

class SettingsScreen(MDScreen):
    def toggle_dark_mode(self):
        print("تبديل وضع الشاشة...")

    def show_upgrade_info(self):
        self.dialog = MDDialog(
            title="النسخة المدفوعة",
            text="استمتع بـ 5 أصوات (منها صوتك)، ترجمة أوفلاين غير محدودة، وحفظ المستندات المترجمة.",
            buttons=[MDFlatButton(text="قريباً", text_color=[1, 0.8, 0, 1])]
        )
        self.dialog.open()

    def show_about_dialog(self):
        about_text = (
            "ميرور سكربيون: حيث تُصنع البدايات\\n\\n"
            "الوقت هو العملة الأغلى.. نحن لا نقيس أعمارنا بالسنوات، "
            "بل بكل ثانية نصنع فيها إنجازاً حقيقياً.\\n\\n"
            "تذكّر دائماً.. قصتك لا تزال تُكتب."
        )
        self.dialog = MDDialog(
            title="فلسفة ميرور",
            text=about_text,
            buttons=[MDFlatButton(text="استمر", text_color=[1, 1, 1, 1])]
        )
        self.dialog.open()
