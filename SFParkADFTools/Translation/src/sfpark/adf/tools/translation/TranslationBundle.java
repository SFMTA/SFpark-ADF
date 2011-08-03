package sfpark.adf.tools.translation;

enum TranslationBundle {
    AssetManagerBundle,
    OSPManagerBundle,
    PriceChangeManagerBundle,

    ColumnBundle,
    CommonBundle,
    LabelBundle,
    ButtonBundle,
    ErrorBundle;

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final String BaseName;

    private TranslationBundle() {
        this.BaseName = "sfpark.adf.tools.translation." + name();
    }

    public String getBaseName() {
        return BaseName;
    }
}
