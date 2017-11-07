package band.full.testing.video.encoder;

import static band.full.testing.video.encoder.DecoderY4M.decode;
import static band.full.testing.video.encoder.EncoderParameters.HDR10;
import static java.util.Collections.addAll;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Igor Malinin
 */
public class EncoderHDR10 extends EncoderHEVC {
    public static final String MASTER_DISPLAY_PRIMARIES =
            "G(13250,34500)B(7500,3000)R(34000,16000)WP(15635,16450)";

    public static final String MASTER_DISPLAY =
            MASTER_DISPLAY_PRIMARIES + "L(10000000,5)";

    private EncoderHDR10(String name, EncoderParameters parameters)
            throws IOException {
        super(name, parameters);
    }

    @Override
    protected ProcessBuilder createProcessBuilder() {
        ProcessBuilder builder = super.createProcessBuilder();
        List<String> command = builder.command();

        // TODO move to HEVC generic code, use EncoderParameters
        addAll(command, "--profile=main10",
                "--colorprim=bt2020", "--colormatrix=bt2020nc",
                "--chromaloc=2", "--transfer=smpte-st-2084");

        // Allow overriding MaxCLL/MaxFALL/MasterDisplay HDR10 metadata

        if (!command.contains("--master-display")) {
            addAll(command, "--master-display", MASTER_DISPLAY);
        }

        return builder;
    }

    public static void encode(String name, Consumer<EncoderY4M> consumer) {
        encode(name, HDR10, consumer);
    }

    public static void encode(String name,
            Consumer<EncoderY4M> ec, Consumer<DecoderY4M> dc) {
        encode(name, HDR10, ec, dc);
    }

    public static void encode(String name, EncoderParameters parameters,
            Consumer<EncoderY4M> consumer) {
        try (EncoderHDR10 encoder = new EncoderHDR10(name, parameters)) {
            consumer.accept(encoder);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void encode(String name, EncoderParameters parameters,
            Consumer<EncoderY4M> ec, Consumer<DecoderY4M> dc) {
        encode(name, parameters, ec);
        decode(name, parameters, dc);
    }
}

// "--level-idc=51",
// "--ref=5",
// "--limit-refs=0",
// "--min-keyint=2",
// "--keyint=24",
// "--bframes=0",
// "--limit-modes",
// "--no-b-pyramid",
// "--rd=4",
// "--rskip",
// "--psy-rd=2.00",
// "--psy-rdoq=1.00",
// "--bitrate=40000",
// "--vbv-maxrate=40000",
// "--vbv-bufsize=40000",
// "--vbv-init=0.9",
// "--aq-mode=1",
// "--aq-strength=1.00",
// "--cutree",
// "--qg-size=32",
// "--no-rc-grain",
// "--tune", "grain",
