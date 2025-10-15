{
  description = "Development environment for the compostor LibGDX project";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    flake-parts.inputs.nixpkgs-lib.follows = "nixpkgs";
  };

  outputs = inputs@{ flake-parts, nixpkgs, ... }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
        "x86_64-darwin"
      ];

      perSystem = { pkgs, lib, system, ... }:
        let
          jdk = pkgs.jdk21;
          gradle = pkgs.gradle;
          kotlin = pkgs.kotlin;
          kotlinLangServer = pkgs.kotlin-language-server;
          linuxRuntimeLibs =
            if pkgs.stdenv.isLinux then
              with pkgs; [
                libGL
                libGLU
                pulseaudio
              ]
              ++ (with pkgs.xorg; [
                libX11
                libXcursor
                libXext
                libXi
                libXinerama
                libXrandr
                libXxf86vm
                libXtst
                libxcb
              ])
            else
              [ ];
          ldPath = lib.makeLibraryPath linuxRuntimeLibs;
        in {
          devShells.default = pkgs.mkShell {
            name = "compostor-shell";
            packages = [
              jdk
              gradle
              kotlin
              kotlinLangServer
            ];

            shellHook = lib.concatStringsSep "\n" (
              [
                "export JAVA_HOME=${jdk}"
                "export _JAVA_AWT_WM_NONREPARENTING=1"
              ]
              ++ lib.optionals pkgs.stdenv.isLinux [
                "export LD_LIBRARY_PATH=${ldPath}:$LD_LIBRARY_PATH"
              ]
            );
          };
        };
    };
}
