package ALTERCAST.aLterMS.converter;

import ALTERCAST.aLterMS.domain.Assignment;
import ALTERCAST.aLterMS.domain.Submit;
import ALTERCAST.aLterMS.domain.SubmitFile;
import ALTERCAST.aLterMS.domain.User;
import ALTERCAST.aLterMS.dto.SubmitRequestDTO;
import ALTERCAST.aLterMS.dto.SubmitResponseDTO;

import java.util.Optional;

import static org.reflections.util.ConfigurationBuilder.build;

public class SubmitConverter {
    public static SubmitFile toSubmitFile(Submit submit, String fileUrl) {
        return SubmitFile.builder()
                .submit(submit)
                .fileUrl(fileUrl)
                .build();
    }

    public static Submit toSubmit(SubmitRequestDTO.createSubmitRequestDTO request, Assignment assignment, User user) {
        return Submit.builder()
                .assignment(assignment)
                .user(user)
                .writer(user.getName())
                .comment(request.getComment())
                .state(true)
                .build();
    }

    public static SubmitResponseDTO.createSubmitResultDTO toCreateSubmitResultDTO(Submit submit) {
        return SubmitResponseDTO.createSubmitResultDTO.builder()
                .submitId(submit.getId())
                .build();
    }

    public static SubmitResponseDTO.getSubmitResultDTO toGetSubmitReslutDTO(Submit submit) {
        return SubmitResponseDTO.getSubmitResultDTO.builder()
                .comment(submit.getComment())
                .createdAt(submit.getCreatedAt().toString())
                .updatedAt(submit.getUpdatedAt().toString())
                .writer(submit.getWriter())
                .fileUrls(submit.getSubmitFiles().stream().map(SubmitFile::getFileUrl).toList())
                .build();
    }
}
