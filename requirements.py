import subprocess

def install_requirements():
    libraries = ["kivy", "kivymd", "pillow"]
    for lib in libraries:
        try:
            subprocess.check_call(["pip", "install", lib])
            print(f"Successfully installed {lib}")
        except:
            print(f"Failed to install {lib}")

if __name__ == "__main__":
    install_requirements()
