export interface IPrescription {
    id?: number;
    phonenumber?: string;
    patientName?: string;
    doctorName?: string;
    rightVision?: string;
    leftVision?: string;
    powerDetails?: any;
    continousWear?: boolean;
}

export class Prescription implements IPrescription {
    constructor(
        public id?: number,
        public phonenumber?: string,
        public patientName?: string,
        public doctorName?: string,
        public rightVision?: string,
        public leftVision?: string,
        public powerDetails?: any,
        public continousWear?: boolean
    ) {
        this.continousWear = this.continousWear || false;
    }
}
