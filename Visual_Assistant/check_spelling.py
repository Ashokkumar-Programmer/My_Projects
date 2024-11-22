from spellchecker import SpellChecker

def correct_spelling(incorrect_word):
    spell = SpellChecker()
    return spell.correction(incorrect_word)

incorrect_word = "WHHA"
corrected_word = correct_spelling(incorrect_word)
print(f"Original word: {incorrect_word}, Corrected word: {corrected_word}")
