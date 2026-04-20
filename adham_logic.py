from kivy.utils import platform

def get_translator(target_code):
    if platform == 'android':
        from jnius import autoclass
        TranslateOptions = autoclass('com.google.mlkit.nl.translate.TranslatorOptions')
        Translation = autoclass('com.google.mlkit.nl.translate.Translation')
        TranslateLanguage = autoclass('com.google.mlkit.nl.translate.TranslateLanguage')

        options = TranslateOptions.Builder() \
            .setSourceLanguage(TranslateLanguage.ARABIC) \
            .setTargetLanguage(TranslateLanguage.fromLanguageTag(target_code)) \
            .build()
        return Translation.getClient(options)
    return None

def translate_now(text, lang_code, callback):
    translator = get_translator(lang_code)
    if translator:
        translator.translate(text).addOnSuccessListener(callback)
