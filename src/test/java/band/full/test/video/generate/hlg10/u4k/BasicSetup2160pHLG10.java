package band.full.test.video.generate.hlg10.u4k;

import static band.full.test.video.generator.GeneratorFactory.HEVC;
import static band.full.video.encoder.EncoderParameters.HLG10;

import band.full.test.video.executor.GenerateVideo;
import band.full.test.video.generator.BasicSetupHDRBase;

/**
 * @author Igor Malinin
 */
@GenerateVideo
public class BasicSetup2160pHLG10 extends BasicSetupHDRBase {
    public BasicSetup2160pHLG10() {
        super(HEVC, HLG10, "UHD4K/HLG10", "U4K_HLG10");
    }
}
