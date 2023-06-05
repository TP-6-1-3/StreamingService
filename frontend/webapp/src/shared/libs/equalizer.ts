import { $currentTrackAudioObj, setCurrentTrackAudioObjFx } from "../stores/tracks";

export class Equalizer {
    private audioContext: AudioContext;
    private sourceNode?: any;
    private destinationNode: AudioDestinationNode;
    private gainNodes: GainNode[];
    private filters: BiquadFilterNode[];

    constructor(audioContext: AudioContext) {
        this.audioContext = audioContext;
        this.gainNodes = [];
        this.filters = [];
        this.destinationNode = this.audioContext.destination;
    }

    public initialize(audioBuffer: AudioBuffer): void {
        this.sourceNode = this.audioContext.createBufferSource();
        this.sourceNode.buffer = audioBuffer;
        this.destinationNode = this.audioContext.destination;

        this.createFilters();
        this.connect();
    }

    private createFilters(): void {
        const bands = 10;
        const frequencyRange = this.audioContext.sampleRate / 2;
        const frequencies = this.getFrequencyRange(bands, frequencyRange);

        for (let i = 0; i < bands; i++) {
            const filter = this.audioContext.createBiquadFilter();
            filter.type = 'peaking';
            filter.frequency.value = frequencies[i];
            filter.Q.value = 1;

            const gain = this.audioContext.createGain();
            gain.gain.value = 1;

            filter.connect(gain);
            gain.connect(this.destinationNode);

            this.gainNodes.push(gain);
            this.filters.push(filter);
        }
    }

    private connect(): void {
        const audioObj = $currentTrackAudioObj.getState();

        if (this.sourceNode)
        this.sourceNode.connect(this.filters[0]);

        for (let i = 0; i < this.filters.length - 1; i++) {
            this.filters[i].connect(this.filters[i + 1]);
        }

        this.filters[this.filters.length - 1].connect(this.destinationNode);

        const currentTime = this.audioContext.currentTime;

        audioObj.stop(currentTime);
        audioObj.disconnect();

        // const audioBufferSourceNode = this.audioContext.createBufferSource();
        // audioBufferSourceNode.buffer = this.sourceNode.buffer;
        // audioBufferSourceNode.connect(this.destinationNode);
        // startTime = context.currentTime;
        // const startTime = audioContext.currentTime;

        // audioBufferSourceNode.start(currentTime);
        this.sourceNode.start();

        setCurrentTrackAudioObjFx(this.sourceNode);
    }

    public setGain(band: number, gain: number): void {
        if (band >= 0 && band < this.gainNodes.length) {
            this.gainNodes[band].gain.value = gain;
        }
    }

    private getFrequencyRange(bands: number, maxFrequency: number): number[] {
        const minFrequency = 20; // Minimum frequency (20 Hz)
        const logMin = Math.log10(minFrequency);
        const logMax = Math.log10(maxFrequency);
        const step = (logMax - logMin) / (bands + 1);
        const frequencies = [];

        for (let i = 1; i <= bands; i++) {
            const frequency = Math.pow(10, logMin + step * i);
            frequencies.push(frequency);
        }

        return frequencies;
    }
}